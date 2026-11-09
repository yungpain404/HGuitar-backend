-- V20: Add created_at to learning_method_translations to match JPA entity
ALTER TABLE learning.learning_method_translations
    ADD created_at DATETIME2(3) NOT NULL DEFAULT SYSUTCDATETIME();
