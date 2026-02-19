FROM maven:3.10.1-jdk-21 AS build
WORKDIR /build

# Copy only the rule-engine-service module
COPY rule-engine-service ./rule-engine-service

# Build the module
RUN mvn -f rule-engine-service/pom.xml -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar
COPY --from=build /build/rule-engine-service/target/rule-engine-service-0.0.1-SNAPSHOT.jar app.jar

ENV SERVER_PORT=8083
EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
