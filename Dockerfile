FROM bellsoft/liberica-openjdk-debian:17

WORKDIR /app

COPY target/task-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 7070

ENTRYPOINT ["java", "-jar", "app.jar"]