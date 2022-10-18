FROM eclipse-temurin:17-jre-focal
EXPOSE 8080
ARG JAR_FILE=impl/target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "-Dserver.port=$PORT", "/application.jar", "--spring.profiles.active=heroku", "-Xmx128m"]