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

function schemaLoad() {
  local db_username="$1"
  local db_password="$2"
  sqlplus "$db_username/$db_password@//$connect_identifier" @db/drop.sql
  sqlplus "$db_username/$db_password@//$connect_identifier" @db/schema.sql
  sqlplus "$db_username/$db_password@//$connect_identifier" @db/alter.sql
  #sqlplus "$db_username/$db_password@//$connect_identifier" @db/permissions.sql
  sqlplus "$db_username/$db_password@//$connect_identifier" @db/view.sql
}

schemaLoad "mridb_dev"  "mridb_dev"
schemaLoad "mridb_test" "mridb_test"
