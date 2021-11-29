#!/usr/bin/env bash


## Fail Fast
set -o noclobber  # Avoid overlay files (echo "hi" > foo)
set -o errexit    # Used to exit upon error, avoiding cascading errors
set -o pipefail   # Unveils hidden failures
set -o nounset    # Exposes unset variables

## Debug
#set -o noexec     # Read commands in script, don't execute them (syntax check).
#set -o noglob     # Disable file name expansion (globbing).
#set -o verbose    # Prints shell input lines as they are read.
#set -o xtrace     # Print command traces before executing command.


source bin/_database.sh

# Cleanup old logs
rm -f tmp/*.ctl.log
rm -f tmp/sqlldr-*.log

sqlplus_cmd "$DB_USERNAME/$DB_PASSWORD@//$connect_identifier" @db/drop.sql
sqlplus_cmd "$DB_USERNAME/$DB_PASSWORD@//$connect_identifier" @db/schema.sql

for file in db/loaders/*.ctl; do
  echo "***********************************************************$file"
  [ -e "$file" ] || continue # guard against no globs
  #sqlldr_cmd userid="$DB_USERNAME/$DB_PASSWORD@$connect_identifier AS SYSDBA" \
  # "\' -> \'\''
  # "'  -> '\''
  # \'  -> '\''
  # '   -> 
  # \"  -> "
  # userid="$DB_USERNAME/$DB_PASSWORD@$DB_SERVICE_NAME", \
  sqlldr_cmd "$file"
done

sqlplus_cmd "$DB_USERNAME/$DB_PASSWORD@$connect_identifier" @db/alter.sql
#sqlplus_cmd "$DB_USERNAME/$DB_PASSWORD@$connect_identifier" @db/permissions.sql
sqlplus_cmd "$DB_USERNAME/$DB_PASSWORD@$connect_identifier" @db/view.sql
