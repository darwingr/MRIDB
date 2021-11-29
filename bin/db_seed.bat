sqlplus %DB_USERNAME%/%DB_PASSWORD%@csci275 @db\drop.sql
sqlplus %DB_USERNAME%/%DB_PASSWORD%@csci275 @db\schema.sql
sqlplus %DB_USERNAME%/%DB_PASSWORD%@csci275 @db\alter.sql
sqlplus %DB_USERNAME%/%DB_PASSWORD%@csci275 @db\permissions.sql
sqlplus %DB_USERNAME%/%DB_PASSWORD%@csci275 @db\view.sql

rm tmp/*.ctl.log

for /f %%f in ('dir /b db\loaders\') do (
	sqlldr userid=%DB_USERNAME%/%DB_PASSWORD%@csci275 control=db\loaders\%%f log=tmp\sqlldr-%%f.log bad=tmp\sqlldr-bad-%%f.log
)
