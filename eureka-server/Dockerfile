FROM openjdk:17-jdk-slim

LABEL maintainer="Student Management System"
LABEL description="Eureka Service Registry"

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Create app directory
WORKDIR /app

# Copy maven build output
COPY target/eureka-server-*.jar app.jar

# Expose port
EXPOSE 8761

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8761/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
