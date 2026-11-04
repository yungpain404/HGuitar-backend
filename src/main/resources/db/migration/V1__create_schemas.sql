-- V1: Create all application schemas
-- Run once on a fresh database before any table migrations.

IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'auth')
    EXEC('CREATE SCHEMA auth');

IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'learning')
    EXEC('CREATE SCHEMA learning');

IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'content')
    EXEC('CREATE SCHEMA content');

IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'music')
    EXEC('CREATE SCHEMA music');

IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'assessment')
    EXEC('CREATE SCHEMA assessment');

IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'progress')
    EXEC('CREATE SCHEMA progress');

IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'platform')
    EXEC('CREATE SCHEMA platform');
