# Use OpenJDK 21 as base image
FROM openjdk:21-jdk-slim

# Set working directory in container
WORKDIR /app

# Copy the JAR file into the container
COPY target/Student-login-project-0.0.1-SNAPSHOT.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8181

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]