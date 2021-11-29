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
cdb_connect_identifier="$DB_HOST:$DB_PORT/ORCLCDB.localdomain" # admin use
pdb_connect_identifier="$DB_HOST:$DB_PORT/$DB_SERVICE_NAME"
connect_identifier="$pdb_connect_identifier"


sqlldr_cmd() {
  local db_username="$1"
  local db_password="$2"
  local ctl_file="$3"
  sqlldr \
    "${db_username}/${db_password}@$connect_identifier"  \
    control="$ctl_file" \
    log=tmp/sqlldr-"$(basename "${file}" .ctl)".log \
    bad=tmp/sqlldr-bad-"$(basename "${file}" .ctl)".log
  retcode=`echo $?`
  case "$retcode" in
    0) echo "SQL*Loader execution successful" ;;
    1) echo "SQL*Loader execution exited with EX_FAIL, see logfile" ;;
    2) echo "SQL*Loader execution exited with EX_WARN, see logfile" ;;
    3) echo "SQL*Loader execution encountered a fatal error" ;;
    *) echo "unknown return code";;
  esac
}
