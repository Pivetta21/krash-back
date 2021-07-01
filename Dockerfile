FROM openjdk:11

WORKDIR /app

COPY target/krash-back-0.0.1-SNAPSHOT.jar /app/application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]

