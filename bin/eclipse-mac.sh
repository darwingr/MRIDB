#!/usr/bin/env bash

if [ ! -f .env ]; then
  echo "Project is missing a '.env' file!"
  echo
  echo "You can use the file '.env.template' as a template, just be sure to"
  echo "fill in the necessary environment variables."
  exit -1
fi
export $(egrep -v '^#' .env | xargs)

open /Applications/Eclipse.app
