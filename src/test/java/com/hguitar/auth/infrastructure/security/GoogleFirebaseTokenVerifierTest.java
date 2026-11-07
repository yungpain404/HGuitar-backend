package com.hguitar.auth.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hguitar.auth.application.dto.FirebaseIdentity;
import com.hguitar.common.exception.BusinessRuleException;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class GoogleFirebaseTokenVerifierTest {

    private static final String PROJECT_ID = "hguitar-test";
    private static final String KEY_ID = "test-kid";

    @Mock FirebasePublicKeyProvider keyProvider;

    private GoogleFirebaseTokenVerifier verifier;
    private KeyPair keyPair;

    @BeforeEach
    void setUp() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        keyPair = generator.generateKeyPair();

        verifier = new GoogleFirebaseTokenVerifier(
                keyProvider, new ObjectMapper(), PROJECT_ID);
        lenient().when(keyProvider.getKey(KEY_ID))
                .thenReturn((java.security.interfaces.RSAPublicKey) keyPair.getPublic());
    }

    private String token(String audience, boolean verified, Instant expiration) {
        return Jwts.builder()
                .header().keyId(KEY_ID).and()
                .subject("firebase-uid-123")
                .issuer("https://securetoken.google.com/" + PROJECT_ID)
                .audience().add(audience).and()
                .claim("email", "student@example.com")
                .claim("email_verified", verified)
                .claim("name", "Google Student")
                .claim("picture", "https://example.com/avatar.png")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(expiration))
                .signWith(keyPair.getPrivate())
                .compact();
    }

    @Test
    @DisplayName("verify — accepts a valid Firebase token and maps the identity")
    void verify_validToken() {
        FirebaseIdentity identity = verifier.verify(
                token(PROJECT_ID, true, Instant.now().plusSeconds(3600)));

        assertThat(identity.subject()).isEqualTo("firebase-uid-123");
        assertThat(identity.email()).isEqualTo("student@example.com");
        assertThat(identity.displayName()).isEqualTo("Google Student");
        assertThat(identity.avatarUrl()).isEqualTo("https://example.com/avatar.png");
    }

    @Test
    @DisplayName("verify — rejects a token for another Firebase project")
    void verify_wrongAudience() {
        assertThatThrownBy(() -> verifier.verify(
                token("another-project", true, Instant.now().plusSeconds(3600))))
                .isInstanceOf(BusinessRuleException.class);
    }

    @Test
    @DisplayName("verify — rejects an unverified email")
    void verify_emailNotVerified() {
        assertThatThrownBy(() -> verifier.verify(
                token(PROJECT_ID, false, Instant.now().plusSeconds(3600))))
                .isInstanceOf(BusinessRuleException.class);
    }

    @Test
    @DisplayName("verify — rejects an expired token")
    void verify_expiredToken() {
        assertThatThrownBy(() -> verifier.verify(
                token(PROJECT_ID, true, Instant.now().minusSeconds(60))))
                .isInstanceOf(BusinessRuleException.class);
    }

    @Test
    @DisplayName("verify — reports a clear error when Firebase is not configured")
    void verify_notConfigured() {
        GoogleFirebaseTokenVerifier unconfigured = new GoogleFirebaseTokenVerifier(
                keyProvider, new ObjectMapper(), "");

        assertThatThrownBy(() -> unconfigured.verify(token(PROJECT_ID, true, Instant.now().plusSeconds(600))))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("not configured");
    }
}
