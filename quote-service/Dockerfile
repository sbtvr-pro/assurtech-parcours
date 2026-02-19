FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# Copy only the quote-service module to keep build context small
COPY quote-service ./quote-service

# Build the project (skip tests for faster build)
RUN mvn -f quote-service/pom.xml -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /build/quote-service/target/quote-service-0.0.1-SNAPSHOT.jar app.jar

ENV SERVER_PORT=8082
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]

