FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/eureka-client-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} eureka-client.jar
ENTRYPOINT ["java", "-jar", "/eureka-client.jar"]