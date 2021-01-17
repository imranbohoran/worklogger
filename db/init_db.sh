#!/bin/bash
set -e

psql -h localhost -v ON_ERROR_STOP=1 --username "${PG_USER}" --dbname "postgres" <<-EOSQL
    DROP DATABASE IF EXISTS worklog;
    DROP ROLE IF EXISTS worklog;
    CREATE ROLE worklog WITH LOGIN PASSWORD 'worklog-pass';
    CREATE DATABASE worklog WITH OWNER worklog;
    GRANT ALL PRIVILEGES ON DATABASE worklog TO worklog;
EOSQL