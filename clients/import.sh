#!/usr/bin/env sh

the_classpath=${WORKLOG_PATH:-.}

echo "Using classpath: ${the_classpath} - The path can be set by using [ export WORKLOG_PATH=<your-path> ]"

java -cp "${the_classpath}" com.tib.worklogger.clients.cli.TextFileImporter "$1" "$2"
