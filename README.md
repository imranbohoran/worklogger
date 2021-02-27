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
This will generate the api documentation in the `build/asciidoc/html5/` folder

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
./clients/import.sh
````

## Build and run a docker image
#### Building the image
```
./gradlew :spring-boot-application:bootBuildImage --imageName=tib/worklogger
```
Using the Spring Boot buildpack reduces the image size and builds an 
Open Container Initiative (OCI) image. 

The image could also be build using the standard approach;
```
docker build -t tib/worklogger -f spring-boot-application/Dockerfile
```

#### Running the docker image

```
docker run -p 9000:9000 -e DB_HOST_AND_PORT=<db-host:db-port> tib/worklogger
```
The `<db-host>` and `<db-port>` need to be passed in as an environment variable. 

If using the docker composition in the project for the db, the host and port can be provided as `docker.for.mac.localhost:5432`
