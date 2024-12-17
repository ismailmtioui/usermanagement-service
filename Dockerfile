# Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/usermanagement-service-0.0.1-SNAPSHOT.jar usermanagement-service.jar

# Expose the application port
EXPOSE 9093

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "usermanagement-service.jar"]
