FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/feign-client-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} feign-client.jar
ENTRYPOINT ["java","-jar","/feign-client.jar"]