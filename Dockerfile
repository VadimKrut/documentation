# 1st stage: Build the app
FROM maven:3.9.7-sapmachine-22 AS build

# Set the working directory inside the container
ENV APP_HOME /app
WORKDIR $APP_HOME

# Copy the project's POM file and download dependencies
COPY pom.xml $APP_HOME/
RUN mvn dependency:go-offline -DskipTests

# Copy the entire project directory into the container
COPY . $APP_HOME

# Copy the configuration file to the appropriate location
COPY config/config.yml $APP_HOME/src/main/resources/application.yml

# Build the project
RUN mvn clean install -DskipTests

# 2nd stage: Build the runtime image
FROM sapmachine:22

# Set the working directory inside the container
ENV APP_HOME /app
WORKDIR $APP_HOME

# Copy the JAR file from the build stage into the runtime image
COPY --from=build $APP_HOME/target/documentation.jar ./

# Specify the command to run when the container starts
CMD ["java", "--enable-preview", "-jar", "documentation.jar"]

# Expose the port that the application will listen on
EXPOSE 2493