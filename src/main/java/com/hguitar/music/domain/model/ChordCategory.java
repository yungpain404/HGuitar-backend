package com.hguitar.music.domain.model;

/**
 * Chord family / quality used to group the chord library for beginners.
 * Kept separate from guitar type because acoustic and classical share the
 * same chord shapes in this application.
 */
public enum ChordCategory {
    MAJOR,
    MINOR,
    DOMINANT_7,
    MAJOR_7,
    MINOR_7,
    SUSPENDED
}
