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

sqlplus_cmd "$DB_USERNAME/$DB_PASSWORD@//$connect_identifier" @db/drop.sql
sqlplus_cmd "$DB_USERNAME/$DB_PASSWORD@//$connect_identifier" @db/schema.sql

sqlplus_cmd "$DB_USERNAME/$DB_PASSWORD@$connect_identifier" @db/alter.sql
#sqlplus_cmd "$DB_USERNAME/$DB_PASSWORD@$connect_identifier" @db/permissions.sql
sqlplus_cmd "$DB_USERNAME/$DB_PASSWORD@$connect_identifier" @db/view.sql
