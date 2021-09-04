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


# Load Environment
if [ ! -f .env ]; then
  echo "Project is missing a '.env' file!"
  echo
  echo "You can use the file '.env.template' as a template, just be sure to"
  echo "fill in the necessary environment variables."
  exit 2
else
  set -o allexport
  source .env
  set +o allexport
fi


# Cleanup old logs
rm -f tmp/*.ctl.log
rm -f tmp/sqlldr-*.log

sqlplus "$DB_USERNAME/$DB_PASSWORD"@csci275 @db/schema.sql

for file in db/loaders/*.ctl; do
  echo "***********************************************************$file"
  [ -e "$file" ] || continue # guard against no globs
  sqlldr userid="$DB_USERNAME/$DB_PASSWORD"@csci275 \
    control=$file \
    log=tmp/sqlldr-$(basename "$file" .ctl).log \
    bad=tmp/sqlldr-bad-$(basename "$file" .ctl).log
done

sqlplus "$DB_USERNAME/$DB_PASSWORD"@csci275 @db/alter.sql
sqlplus "$DB_USERNAME/$DB_PASSWORD"@$csci275 @db/permissions.sql
sqlplus "$DB_USERNAME/$DB_PASSWORD"@csci275 @db/view.sql
