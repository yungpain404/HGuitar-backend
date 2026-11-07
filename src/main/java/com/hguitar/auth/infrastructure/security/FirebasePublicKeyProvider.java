package com.hguitar.auth.infrastructure.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hguitar.common.exception.BusinessRuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Downloads and caches the public certificates Google uses to sign Firebase ID
 * tokens. The key set is refreshed according to the Cache-Control max-age
 * returned by Google.
 */
@Component
public class FirebasePublicKeyProvider {

    private static final Logger log = LoggerFactory.getLogger(FirebasePublicKeyProvider.class);
    private static final String CERTS_URL =
            "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com";
    private static final Pattern MAX_AGE = Pattern.compile("max-age=(\\d+)");
    private static final long DEFAULT_TTL_SECONDS = 3600;
    private static final long MIN_TTL_SECONDS = 60;

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    private volatile Map<String, RSAPublicKey> keys = Map.of();
    private volatile Instant expiresAt = Instant.EPOCH;

    public FirebasePublicKeyProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5_000);
        requestFactory.setReadTimeout(8_000);
        this.restClient = RestClient.builder()
                .requestFactory(requestFactory)
                .build();
    }

    public RSAPublicKey getKey(String keyId) {
        if (keyId == null || keyId.isBlank()) {
            throw new BusinessRuleException("Google sign-in token is invalid.");
        }

        refreshIfNeeded();
        RSAPublicKey key = keys.get(keyId);
        if (key == null) {
            // Google may have rotated keys between cache refreshes.
            refresh(true);
            key = keys.get(keyId);
        }
        if (key == null) {
            throw new BusinessRuleException("Google sign-in token is invalid.");
        }
        return key;
    }

    private void refreshIfNeeded() {
        if (!keys.isEmpty() && Instant.now().isBefore(expiresAt)) return;
        refresh(false);
    }

    private synchronized void refresh(boolean force) {
        if (!force && !keys.isEmpty() && Instant.now().isBefore(expiresAt)) return;

        try {
            ResponseEntity<JsonNode> response = restClient.get()
                    .uri(CERTS_URL)
                    .retrieve()
                    .toEntity(JsonNode.class);

            JsonNode body = response.getBody();
            if (body == null || !body.isObject()) {
                throw new IllegalStateException("Google certificate response was empty.");
            }

            Map<String, RSAPublicKey> refreshed = new HashMap<>();
            body.fields().forEachRemaining(entry -> {
                RSAPublicKey publicKey = parseCertificate(entry.getValue().asText());
                if (publicKey != null) refreshed.put(entry.getKey(), publicKey);
            });

            if (refreshed.isEmpty()) {
                throw new IllegalStateException("No usable Firebase public keys.");
            }

            this.keys = Map.copyOf(refreshed);
            this.expiresAt = Instant.now().plusSeconds(resolveTtl(response));
            log.debug("Refreshed {} Firebase public keys", refreshed.size());
        } catch (Exception e) {
            log.warn("Could not refresh Firebase public keys: {}", e.getMessage());
            if (keys.isEmpty()) {
                throw new BusinessRuleException("Google sign-in is temporarily unavailable.");
            }
            // Keep serving the previously cached keys if Google is unreachable.
        }
    }

    private long resolveTtl(ResponseEntity<JsonNode> response) {
        String cacheControl = response.getHeaders().getCacheControl();
        if (cacheControl != null) {
            Matcher matcher = MAX_AGE.matcher(cacheControl);
            if (matcher.find()) {
                try {
                    return Math.max(MIN_TTL_SECONDS, Long.parseLong(matcher.group(1)));
                } catch (NumberFormatException ignored) {
                    // Fall through to the default.
                }
            }
        }
        return DEFAULT_TTL_SECONDS;
    }

    private RSAPublicKey parseCertificate(String pem) {
        if (pem == null || pem.isBlank()) return null;

        try {
            String base64 = pem
                    .replace("-----BEGIN CERTIFICATE-----", "")
                    .replace("-----END CERTIFICATE-----", "")
                    .replaceAll("\\s", "");
            byte[] der = Base64.getDecoder().decode(base64);
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) factory.generateCertificate(
                    new ByteArrayInputStream(der));
            PublicKey publicKey = certificate.getPublicKey();
            return publicKey instanceof RSAPublicKey rsa ? rsa : null;
        } catch (Exception e) {
            log.warn("Ignoring an unreadable Firebase public certificate: {}", e.getMessage());
            return null;
        }
    }
}
