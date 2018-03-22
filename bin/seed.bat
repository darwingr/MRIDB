rm tmp/*.ctl.log

%SQLPLUS_PATH% %DB_USERNAME%/%DB_PASSWORD%@csci275 @db\schema.sql
for /f %%f in ('dir /b db\loaders\') do (
	%SQLLDR_PATH% userid=%DB_USERNAME%/%DB_PASSWORD%@csci275 control=db\loaders\%%f log=tmp\sqlldr-%%f.log bad=tmp\sqlldr-bad-%%f.log
)
%SQLPLUS_PATH% %DB_USERNAME%/%DB_PASSWORD%@csci275 @db\alter.sql
