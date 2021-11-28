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

# <connect_identifier> can be in the form of Net Service Name or Easy Connect.
#    @[<net_service_name> | [//]Host[:Port]/<service_name>]
connect_identifier="$DB_HOST:$DB_PORT/$DB_SERVICE_NAME"

# IF paths for sqlplus & sqlldr are not set
#   THEN assume it is the local docker instance for dev
# IGNORE locally installed sqlldr/sqlplus binaries
#XXX more reliable container name
container_name="mridb-oracledb-1"
docker_bash() {
  docker exec -it $container_name bash -c "
  source /home/oracle/.bashrc
  $*
  "
}
cmd_exists() {
  command -v "$1" &> /dev/null
}
sqlplus_cmd() {
  if [ -z "${SQLPLUS_PATH-}" ]
  then
    if cmd_exists sqlplus
    then
      echo "Found local command: $(which sqlplus)"
      sqlplus "$@"
      return
    fi
    docker_bash sqlplus "$*"
  else
    "$SQLPLUS_PATH" "$@"
  fi
}
sqlldr_cmd() {
  local ctl_file
  ctl_file="$1"
  if [ -z "${SQLLDR_PATH-}" ]
  then
    if cmd_exists sqlldr
    then
      echo "Found local command: $(which sqlldr)"
      sqlldr \
        "${DB_USERNAME}/${DB_PASSWORD}@$connect_identifier"  \
        control="$ctl_file" \
        log=tmp/sqlldr-"$(basename "${file}" .ctl)".log \
        bad=tmp/sqlldr-bad-"$(basename "${file}" .ctl)".log
      return
    else
      docker_bash sqlldr \
        "${DB_USERNAME}/${DB_PASSWORD}@$connect_identifier" \
        "control=$ctl_file" \
        "bad=/home/oracle/tmp/sqlldr-bad-$(basename "${file}" .ctl).log" \
        "log=/home/oracle/tmp/sqlldr-$(basename "${file}" .ctl).log"
    fi
  else
    "$SQLLDR_PATH" \
      "${DB_USERNAME}/${DB_PASSWORD}@$connect_identifier"  \
      control="$ctl_file" \
      log=tmp/sqlldr-"$(basename "${file}" .ctl)".log \
      bad=tmp/sqlldr-bad-"$(basename "${file}" .ctl)".log
  fi
  retcode=`echo $?`
  case "$retcode" in
    0) echo "SQL*Loader execution successful" ;;
    1) echo "SQL*Loader execution exited with EX_FAIL, see logfile" ;;
    2) echo "SQL*Loader execution exited with EX_WARN, see logfile" ;;
    3) echo "SQL*Loader execution encountered a fatal error" ;;
    *) echo "unknown return code";;
  esac
}

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
