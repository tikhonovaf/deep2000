FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/deep2000.jar
WORKDIR /opt/app
COPY ${JAR_FILE} deep2000.jar
ENTRYPOINT ["java","-jar","deep2000.jar"]