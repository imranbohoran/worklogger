FROM adoptopenjdk/openjdk11:alpine-jre

# Add a specific user to avoid running with root
RUN addgroup -S worklogger && adduser -S worklogger -G worklogger
USER worklogger:worklogger

WORKDIR /app

EXPOSE 9000

COPY build/libs/spring-boot-application*.jar /app/worklogger.jar

ENTRYPOINT ["java","-jar","/app/worklogger.jar"]
