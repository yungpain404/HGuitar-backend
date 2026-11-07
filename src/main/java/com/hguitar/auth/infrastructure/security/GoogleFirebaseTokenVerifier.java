package com.hguitar.auth.infrastructure.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hguitar.auth.application.dto.FirebaseIdentity;
import com.hguitar.auth.domain.service.FirebaseTokenVerifier;
import com.hguitar.common.exception.BusinessRuleException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Locale;

/**
 * Verifies Firebase ID tokens locally against Google's published certificates.
 * No Firebase service account is required: only the Firebase project id is
 * needed to validate issuer and audience.
 */
@Component
public class GoogleFirebaseTokenVerifier implements FirebaseTokenVerifier {

    private static final Logger log = LoggerFactory.getLogger(GoogleFirebaseTokenVerifier.class);
    private static final String ISSUER_PREFIX = "https://securetoken.google.com/";

    private final FirebasePublicKeyProvider publicKeyProvider;
    private final ObjectMapper objectMapper;
    private final String projectId;

    public GoogleFirebaseTokenVerifier(
            FirebasePublicKeyProvider publicKeyProvider,
            ObjectMapper objectMapper,
            @Value("${app.firebase.project-id:}") String projectId) {
        this.publicKeyProvider = publicKeyProvider;
        this.objectMapper = objectMapper;
        this.projectId = projectId == null ? "" : projectId.trim();
    }

    @Override
    public FirebaseIdentity verify(String idToken) {
        if (projectId.isBlank()) {
            throw new BusinessRuleException("Google sign-in is not configured on this server.");
        }
        if (idToken == null || idToken.isBlank()) {
            throw new BusinessRuleException("Google sign-in token is missing.");
        }

        try {
            String keyId = extractKeyId(idToken);
            RSAPublicKey publicKey = publicKeyProvider.getKey(keyId);

            Claims claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .requireIssuer(ISSUER_PREFIX + projectId)
                    .requireAudience(projectId)
                    .build()
                    .parseSignedClaims(idToken)
                    .getPayload();

            String email = claims.get("email", String.class);
            if (email == null || email.isBlank()) {
                throw new BusinessRuleException("The Google account does not expose an email address.");
            }

            Object verified = claims.get("email_verified");
            boolean emailVerified = Boolean.TRUE.equals(verified)
                    || "true".equalsIgnoreCase(String.valueOf(verified));
            if (!emailVerified) {
                throw new BusinessRuleException("The Google account email is not verified.");
            }

            String subject = claims.getSubject();
            if (subject == null || subject.isBlank()) {
                throw new BusinessRuleException("Google sign-in token is invalid.");
            }

            String normalizedEmail = email.trim().toLowerCase(Locale.ROOT);
            String name = claims.get("name", String.class);
            String displayName = name == null || name.isBlank()
                    ? normalizedEmail.substring(0, normalizedEmail.indexOf('@'))
                    : name.trim();

            String picture = claims.get("picture", String.class);
            if (picture != null && !(picture.startsWith("https://") || picture.startsWith("http://"))) {
                picture = null;
            }

            return new FirebaseIdentity(subject, normalizedEmail, displayName, picture);
        } catch (BusinessRuleException e) {
            throw e;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Rejected Firebase ID token: {}", e.getMessage());
            throw new BusinessRuleException("Google sign-in failed.");
        }
    }

    private String extractKeyId(String idToken) {
        try {
            String[] parts = idToken.split("\\.");
            if (parts.length < 2) {
                throw new BusinessRuleException("Google sign-in token is invalid.");
            }
            byte[] headerBytes = Base64.getUrlDecoder().decode(parts[0]);
            JsonNode header = objectMapper.readTree(new String(headerBytes, StandardCharsets.UTF_8));
            String keyId = header.path("kid").asText("");
            if (keyId.isBlank()) {
                throw new BusinessRuleException("Google sign-in token is invalid.");
            }
            return keyId;
        } catch (BusinessRuleException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Google sign-in token is invalid.");
        }
    }
}
