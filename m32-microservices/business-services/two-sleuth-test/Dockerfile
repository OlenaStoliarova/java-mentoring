FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/two-sleuth-test-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} sleuth-test.jar
ENTRYPOINT ["java","-jar","/sleuth-test.jar"]