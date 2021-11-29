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


# TODO move this to a dockerfile build command
# Some vars should be centralized in commited env file for running tests, etc.

: "${DB_ADMIN_USERNAME:-sys}"
: "${DB_ADMIN_PASSWORD:-Oradoc_db1}"

source bin/_database.sh

docker exec -it $container_name bash -c "
  source /home/oracle/.bashrc
  sqlplus sys/Oradoc_db1@$cdb_connect_identifier as sysdba \
    @db/dba_archivelog.sql
"

docker exec -it $container_name bash -c "
  source /home/oracle/.bashrc
  sqlplus sys/Oradoc_db1@$pdb_connect_identifier as sysdba \
    @db/dba_create.sql mridb_dev
"
