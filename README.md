# The Work Logger

A work logger to simply log the work

## Prerequisites
- Java 11
- Gradle
- Docker (used to setup a postgres instance)

## Setup local environment
- Ensure postgres server is running 
- Add an `.env-variavles.properties` file at the root of the project
- Add the postgres user and password as key value pairs    
```
    PG_USER=<the-postgres-user>
    PG_PASSWORD=<the-postgres-password>
```

## Building the application
- Run the environment setup script
```
source load-local-envs.sh
```

- Run gradle to build the application
```
./gradlew clean :application:build
```

## Building the clients

- Run gradle to build the clients
```
./gradlew clean :client:shadow
```


## Generating API documentation
```
./gradlew clean :application:test asciidoctor
```
This will generate the api documentaion in the `build/asciidoc/html5/` folder

## Running the application
```
./gradlew :application:bootRun
```

## Running the CLI client
#### Export the path to the client jar
```
export WORKLOG_PATH=/<PROJECT_ROOT>/worklogger/clients/build/libs/clients-0.0.1-SNAPSHOT-all.jar
```

#### Run the import to import work items under `worklogs`
````
./client/
````