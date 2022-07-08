FROM openjdk:11-jre-slim

ENV APP_HOME=/app

ADD /codingtest/build/libs/codingtest-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "codingtest-0.0.1-SNAPSHOT.jar"]