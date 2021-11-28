-- examples:
--  mridb_test for tests
--  mridb_dev  for development
--  mridb      for production
-- Use the . operator to separate it from the string it's being concatenated in
-- front of. See CONCAT for more.
DEFINE db_env = &1 (CHAR)

-- create the local db tablespaces
DROP TABLESPACE &db_env._pts
  INCLUDING CONTENTS AND DATAFILES
    CASCADE CONSTRAINTS;
DROP TABLESPACE &db_env._tts
  INCLUDING CONTENTS AND DATAFILES
    CASCADE CONSTRAINTS;

CREATE TABLESPACE &db_env._pts
  DATAFILE '&db_env._pts.dbf'
  SIZE 32M
  AUTOEXTEND ON NEXT 32M
  MAXSIZE 4096M
  EXTENT MANAGEMENT LOCAL
  ONLINE;

-- Needed for large data operations during sessions

CREATE TEMPORARY TABLESPACE &db_env._tts
  TEMPFILE '&db_env._tts.dbf'
  SIZE 32M
  AUTOEXTEND ON NEXT 32M
  MAXSIZE 4096M
  EXTENT MANAGEMENT LOCAL;

-- Verify with:
--  SELECT * FROM DBA_TABLESPACES;

DROP USER &db_env CASCADE;

CREATE USER
  &db_env
IDENTIFIED
  BY &db_env
  DEFAULT TABLESPACE &db_env._pts
  TEMPORARY TABLESPACE &db_env._tts
  PROFILE default
  ACCOUNT UNLOCK;
--  QUOTA UNLIMITED ON &db_env._tts

-- drop, insert, update, and delete are implied for owner
GRANT
  CREATE SESSION,
  CREATE TABLE,
  CREATE VIEW,
  CREATE PROCEDURE,
  CREATE SEQUENCE,
  CREATE TYPE,
  CREATE INDEXTYPE,
  UNLIMITED TABLESPACE
TO &db_env;


exit
