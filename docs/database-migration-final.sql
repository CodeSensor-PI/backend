-- FINAL Database Migration Script
-- This script completes the migration by removing old unused columns
-- Execute this script to fix the SQLException: Field 'token' doesn't have a default value

-- 1. First, backup the current table (optional but recommended)
CREATE TABLE IF NOT EXISTS password_reset_token_backup_final AS SELECT * FROM password_reset_token;

-- 2. Clear any existing tokens to avoid conflicts
DELETE FROM password_reset_token;

-- 3. Remove old foreign key constraint if it exists
ALTER TABLE password_reset_token DROP FOREIGN KEY IF EXISTS FK_password_reset_token_paciente;
ALTER TABLE password_reset_token DROP FOREIGN KEY IF EXISTS FKn6s9v3b4ds2id0n6qp1rhu85m;

-- 4. Remove old columns that are no longer used
ALTER TABLE password_reset_token DROP COLUMN IF EXISTS token;
ALTER TABLE password_reset_token DROP COLUMN IF EXISTS paciente_id;

-- 5. Remove old indexes
DROP INDEX IF EXISTS token ON password_reset_token;
DROP INDEX IF EXISTS UK_token ON password_reset_token;

-- 6. Ensure new columns exist with correct types
ALTER TABLE password_reset_token 
  MODIFY COLUMN codigo VARCHAR(6) NOT NULL,
  MODIFY COLUMN email VARCHAR(255) NOT NULL,
  MODIFY COLUMN tipo_usuario VARCHAR(255) NOT NULL;

-- 7. Ensure unique index on codigo exists
CREATE UNIQUE INDEX IF NOT EXISTS UK_codigo ON password_reset_token (codigo);

-- 8. Verify the final structure
DESCRIBE password_reset_token;

-- Expected final structure:
-- +----------------+--------------+------+-----+---------+----------------+
-- | Field          | Type         | Null | Key | Default | Extra          |
-- +----------------+--------------+------+-----+---------+----------------+
-- | id             | bigint       | NO   | PRI | NULL    | auto_increment |
-- | expiry_date    | datetime(6)  | NO   |     | NULL    |                |
-- | codigo         | varchar(6)   | NO   | UNI | NULL    |                |
-- | email          | varchar(255) | NO   |     | NULL    |                |
-- | tipo_usuario   | varchar(255) | NO   |     | NULL    |                |
-- +----------------+--------------+------+-----+---------+----------------+
