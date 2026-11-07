package com.hguitar.auth.domain.service;

import com.hguitar.auth.application.dto.FirebaseIdentity;

/**
 * Verifies a Firebase ID token and returns the trusted identity it represents.
 */
public interface FirebaseTokenVerifier {
    FirebaseIdentity verify(String idToken);
}
