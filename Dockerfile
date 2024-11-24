# Use a base image with OpenJDK
FROM openjdk:17-jdk-slim

# Copy the built JAR file into the container
COPY target/Restaurant_Apis_Scalable_Assignment-0.0.1-SNAPSHOT.jar /app/Restaurant_Apis_Scalable_Assignment-0.0.1-SNAPSHOT.jar

EXPOSE 8081
# Install essential tools for debugging (optional but helpful)
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    wget \
    iputils-ping \
    netcat && \
    rm -rf /var/lib/apt/lists/*
# Set the entrypoint to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/Restaurant_Apis_Scalable_Assignment-0.0.1-SNAPSHOT.jar"]