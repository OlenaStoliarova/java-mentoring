FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8050
ARG JAR_FILE=target/spring-cloud-api-gateway-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} api-gateway.jar
ENTRYPOINT ["java","-jar","/api-gateway.jar"]