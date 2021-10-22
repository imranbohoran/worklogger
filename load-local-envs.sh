#!/usr/bin/env bash

## Expects the enviornment variables to be declared as var_name=value in the ".env-variables.properties" file

while read -r line;
do
  echo "$line";
  export "${line?}"
done < ./.env-variables.properties
