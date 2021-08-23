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


classpath="lib/ojdbc8.jar:build/classes:res:."

JAVA_HOME="$(/usr/libexec/java_home -v 1.8)"
APP_NAME=""
APP_LABEL="jsdb"
APP_DIR_NAME="JSDB.app"

ICON_PATH="Hopstarter-Rounded-Square-Mac-Activity-Monitor.icns"
JAR_NAME=""
DEV_JAR="${APP_LABEL}-dev.jar"
APP_JAR="${APP_LABEL}-app.jar"
RELEASE_DIR="release"

make-classes() {
  mkdir -p build/classes

  javac \
    -Werror \
    -source 1.8 \
    -target 1.8 \
    -classpath "$classpath" \
    -sourcepath src \
    -d build/classes/ \
    src/**/*.java

  echo "[$0] javac complete"
  echo
}

make-dev-jar() {
  make-classes

  rm build/manifest.txt
  echo "Class-Path: lib/ojdbc8.jar" > build/manifest.txt
  jar cvfem "$DEV_JAR" game.GameManager build/manifest.txt \
    -C res . \
    -C build/classes .

  echo "[$0] jar complete"
  echo
}

make-app-jar() {
  make-classes

  jar cvf "$APP_JAR" \
    -C build/classes .

  echo "[$0] jar for app complete"
  echo
}

run-from-class() {
  make-classes

  echo "[CLASSPATH]=$classpath"
  java \
    -verbose \
    -Xdock:name="JSDB" \
    -Xdock:icon="$ICON_PATH" \
    -classpath "${classpath}" \
    game.GameManager
}

run-from-jar() {
  make-dev-jar

  echo "[CLASSPATH]=$classpath"
  java  \
    -verbose \
    -Xdock:name="JSDB" \
    -Xdock:icon="$ICON_PATH" \
    -classpath "${classpath}" \
    -jar "$DEV_JAR"
}

### MAKE MAC APP
# OPTIONS
#   Packr   -> multiple platforms, siging instructions
#   jar2app -> 
#   javapackager

# 3 things we want customized
# - title in top bar of the window (done in engine.GameContainer)
# from here: http://www.oracle.com/technetwork/articles/java/javatomac-140486.html
# - App name in menu bar (use -Xdock:name="" when running java)
# - App name in dock...doesn't change though

# - App icon in the dock: -Xdock:icon="path/to/file.icns"
#   Otherwise change the info.plist from the applescript


# --vmargs Xmx1G XstartOnFirstThread \
# --useZgcIfSupportedOs \
make-mac-app() {
  make-app-jar
  rm -rf "$APP_DIR_NAME"

  java -jar packr-all.jar --verbose \
    --platform mac \
    --cachejre 'build/packr-cache' \
    --minimizejre 'hard' \
    --jdk "$JAVA_HOME" \
    --executable $APP_LABEL \
    --classpath "$APP_JAR" lib/ojdbc8.jar \
    --mainclass game.GameManager \
    --vmargs Xmx1G \
    --resources res/* \
    --icon "$ICON_PATH" \
    --bundle "ca.darweb.${APP_LABEL}" \
    --output "$APP_DIR_NAME"
}


# MAKE ONE SELF-CONTAINED RELEASE JAR
# for Oracle jdk only
#javapackager \
#  -createjar \
#  -Bruntime=${JAVA_HOME} \
#  -appclass game.GameManager \
#  -srcdir . \
#  -classpath lib/ojdbc8.jar \
#  -outfile "${APP_LABEL}Client" \
#  -srcfiles $APP_LABEL.jar lib/ojdbc8.jar


# Check if the script was sourced or run from the command line
if [ "$0" == "$BASH_SOURCE" ]
then
  # Check if the function exists (bash specific)
  if declare -f "$1" > /dev/null
  then
    # call arguments verbatim
    "$@"
  else
    # Show a helpful error
    echo "'$1' is not a known function name" >&2
    exit 1
  fi
fi
