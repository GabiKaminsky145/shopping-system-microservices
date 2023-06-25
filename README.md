# shopping-system-microservices


This project contains a shopping system simulation that uses REST API and Async.
Other features contain a tracing mechanism, discovery server, API Gateway support, authentication, and Dockerize.

## How to run the application using Docker

1. Run `mvn clean package -DskipTests` to build the applications and create the docker image locally.
2. Run `docker-compose up -d` to start the applications.

## How to run the application without Docker

1. Run `mvn clean verify -DskipTests` by going inside each folder to build the applications.
2. After that run `mvn spring-boot:run` by going inside each folder to start the applications.
