#!/usr/bin/env bash

# exit as soon as something fails
set -e
# treat unset variables as an error when performing expansion
set -u

# Load Environment
if [ ! -f .env ]; then
  echo "Project is missing a '.env' file!"
  echo
  echo "You can use the file '.env.template' as a template, just be sure to"
  echo "fill in the necessary environment variables."
  exit 2
fi
export $(egrep -v '^#' .env | xargs)

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
sqlplus "$DB_USERNAME/$DB_PASSWORD"@csci275 @db/view.sql
