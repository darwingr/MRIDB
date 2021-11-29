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

for file in db/loaders/*.ctl; do
  echo "***********************************************************$file"
  [ -e "$file" ] || continue # guard against no globs
  sqlldr_cmd "mridb_dev"  "mridb_dev"  "$file"
  sqlldr_cmd "mridb_test" "mridb_test" "$file"
done
