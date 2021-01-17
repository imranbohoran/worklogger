#!/bin/bash
set -e

echo "Setting up test DB"

PGPASSWORD=${PG_PASSWORD} psql -h localhost -v ON_ERROR_STOP=1 --username "${PG_USER}" --dbname "postgres" <<-EOSQL
    DROP DATABASE IF EXISTS worklog_test;
    DROP ROLE IF EXISTS worklog_test;
    CREATE ROLE worklog_test WITH LOGIN PASSWORD 'worklog_test-pass';
    CREATE DATABASE worklog_test WITH OWNER worklog_test;
    GRANT ALL PRIVILEGES ON DATABASE worklog_test TO worklog_test;
EOSQL