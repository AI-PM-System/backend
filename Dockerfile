# Base image
FROM ubuntu:22.04

# Define arguments
ARG JDBC_DATABASE_URL
ARG JDBC_USERNAME
ARG JDBC_PASSWORD

# Set environment variables
ENV JDBC_DATABASE_URL $JDBC_DATABASE_URL
ENV JDBC_USERNAME $JDBC_USERNAME
ENV JDBC_PASSWORD $JDBC_PASSWORD

# Install OpenJDK 17 slim
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk-headless && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . .

# IMPORTANT, if this line fails because GitHub Action cannot find mvnw,
# it is because the wrapper is not available within the repository.
# Ensure you doesn't ignore the wrapper in .gitignore.
RUN chmod +x mvnw
RUN ./mvnw package

EXPOSE 8080

CMD ["sh", "-c", "java -jar target/backend-0.0.1-SNAPSHOT.jar"]