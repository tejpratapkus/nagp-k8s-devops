# Dockerfile for Spring Boot + PostgreSQL + Maven application

# Use Maven image to build the app
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Final image to run the app
FROM openjdk:17-jdk-slim

# Create app directory
WORKDIR /app

# Copy JAR file from the builder image
COPY --from=builder /app/target/*.jar app.jar

# Expose port (default 8080)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]