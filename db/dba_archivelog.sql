-- initially set to 0
ALTER SYSTEM SET ARCHIVE_LAG_TARGET=1800 SCOPE=BOTH SID='*';

-- real problem is the initial 3 redologs are 10MB max = 10485760 bytes

--ALTER DATABASE DROP LOGFILE GROUP 1;
ALTER DATABASE ADD LOGFILE
  GROUP 1 ('/u04/app/oracle/redo/redo01.dbf') SIZE 128M REUSE;

--ALTER DATABASE DROP LOGFILE GROUP 2;
ALTER DATABASE ADD LOGFILE
  GROUP 2 ('/u04/app/oracle/redo/redo02.dbf') SIZE 128M REUSE;

--ALTER DATABASE DROP LOGFILE GROUP 3;
ALTER DATABASE ADD LOGFILE
  GROUP 3 ('/u04/app/oracle/redo/redo03.dbf') SIZE 128M REUSE;

--ALTER DATABASE DROP LOGFILE GROUP 4;
--ALTER DATABASE ADD LOGFILE
--  GROUP 4 ('/u04/app/oracle/redo/redo04.dbf') SIZE 64M REUSE;

--ALTER DATABASE DROP LOGFILE GROUP 5;
--ALTER DATABASE ADD LOGFILE
--  GROUP 5 ('/u04/app/oracle/redo/redo05.dbf') SIZE 64M REUSE;


exit
