# Set the base image to open jdk 8
FROM openjdk:8

# Run mkdir command to create directory /myapp in this image
RUN mkdir /myapp

# Copy host build directory to /myapp directory in the image
COPY . /myapp

# Set /myapp as the working directory 
WORKDIR /myapp

# Docker container listens to 8080
EXPOSE 8080

# Configure container as an executable
ENTRYPOINT java -jar cmpe282-0.1.0.jar
