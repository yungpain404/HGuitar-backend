package com.hguitar.user.application.service;

import com.hguitar.auth.domain.repository.UserRepository;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.user.application.dto.request.UpdateProfileRequest;
import com.hguitar.user.application.dto.response.UserProfileResponse;
import com.hguitar.user.domain.model.UserProfile;
import com.hguitar.user.domain.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserProfileService {

    private final UserProfileRepository profileRepository;
    private final UserRepository userRepository;

    public UserProfileService(UserProfileRepository profileRepository,
                              UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createProfile(UUID userId, String displayName) {
        UserProfile profile = new UserProfile(
                UUID.randomUUID(), userId, displayName, null, Instant.now(), Instant.now());
        profileRepository.save(profile);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        var profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found."));
        return new UserProfileResponse(user.getId(), user.getEmail(),
                profile.getDisplayName(), profile.getAvatarUrl());
    }

    @Transactional
    public UserProfileResponse updateProfile(UUID userId, UpdateProfileRequest request) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        var profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found."));

        profile.update(request.displayName(), request.avatarUrl());
        profileRepository.save(profile);

        return new UserProfileResponse(user.getId(), user.getEmail(),
                profile.getDisplayName(), profile.getAvatarUrl());
    }

    /**
     * Returns the display name for internal use (e.g. in auth responses).
     */
    public String getDisplayName(UUID userId) {
        return profileRepository.findByUserId(userId)
                .map(UserProfile::getDisplayName)
                .orElse("");
    }
}
