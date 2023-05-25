FROM openjdk:8-jdk-alpine
RUN apk add --no-cache curl
VOLUME /tmp
EXPOSE 8761
ARG JAR_FILE=target/eureka-server-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} eureka-server.jar
ENTRYPOINT ["java","-jar","/eureka-server.jar"]