package com.hguitar.common.domain;

/**
 * Supported content languages.
 */
public enum Language {
    VI,
    EN;

    public static Language fromCode(String code) {
        if (code == null) return EN;
        return switch (code.toLowerCase()) {
            case "vi" -> VI;
            default  -> EN;
        };
    }

    public String toCode() {
        return this.name().toLowerCase();
    }
}
