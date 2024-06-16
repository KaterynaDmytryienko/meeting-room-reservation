## Use an official OpenJDK runtime as a parent image
#FROM openjdk:11
#
## Set the working directory in the container
#WORKDIR /app
#
## Copy the Maven Wrapper files
#COPY mvnw .
#COPY .mvn .mvn
#
## Copy the project files
#COPY pom.xml .
#COPY src src
#
## Build the project using the Maven Wrapper
#RUN chmod +x mvnw
#RUN ./mvnw clean package
#
## Expose the port that the application will run on
#EXPOSE 8081
