
# Create a final image for running the application
FROM openjdk:11-jre-slim
 
# Set the working directory inside the container
WORKDIR /app
 
# Copy the JAR file from the build environment to the final image
COPY  target/WebApp.war ./app.war
 
# Specify the command to run on container startup
CMD ["java", "-war", "app.war"]