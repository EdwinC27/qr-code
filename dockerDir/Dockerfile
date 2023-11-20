# app
FROM eclipse-temurin:17

RUN mkdir /opt/app

COPY ../target/codigo-qr-0.0.1-SNAPSHOT.jar /opt/app/codigo-qr.jar

CMD ["java", "-jar", "/opt/app/codigo-qr.jar", "--spring.profiles.active=docker"]


