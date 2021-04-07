FROM adoptopenjdk/openjdk11:alpine
ARG JAR_FILE=/key-manager-rest/orange-talents-01-template-pix-keymanager-rest/build/libs/*all.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]