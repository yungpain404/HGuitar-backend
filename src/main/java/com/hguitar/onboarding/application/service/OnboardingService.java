package com.hguitar.onboarding.application.service;

import com.hguitar.onboarding.application.dto.request.CompleteOnboardingRequest;
import com.hguitar.onboarding.application.dto.response.OnboardingStatusResponse;
import com.hguitar.onboarding.domain.model.UserPreference;
import com.hguitar.onboarding.domain.repository.UserPreferenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class OnboardingService {

    private final UserPreferenceRepository preferenceRepository;

    public OnboardingService(UserPreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    @Transactional(readOnly = true)
    public OnboardingStatusResponse getStatus(UUID userId) {
        return preferenceRepository.findByUserId(userId)
                .map(p -> new OnboardingStatusResponse(p.isOnboardingCompleted(), p.getGuitarType()))
                .orElse(new OnboardingStatusResponse(false, null));
    }

    @Transactional
    public OnboardingStatusResponse complete(UUID userId, CompleteOnboardingRequest request) {
        UserPreference preference = preferenceRepository.findByUserId(userId)
                .orElse(new UserPreference(UUID.randomUUID(), userId, null, false, Instant.now(), Instant.now()));

        preference.completeOnboarding(request.guitarType());
        preference = preferenceRepository.save(preference);

        return new OnboardingStatusResponse(preference.isOnboardingCompleted(), preference.getGuitarType());
    }
}
