# Star Wars Planet API

This is a RESTful API built with Spring Boot that provides information about planets in the Star Wars universe.

## Features

- Create a new planet
- Retrieve a planet by ID
- Retrieve a planet by name
- List all planets, with optional filters by terrain and climate
- Remove a planet by ID

## Technologies Used

- [Mysql](https://dev.mysql.com/downloads/mysql/)
- [Java](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Testing](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#testing-introduction)
- [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito](https://site.mockito.org)
- [AssertJ](https://github.com/assertj/assertj)

## API Endpoints

- `POST /planets`: Create a new planet. The request body should be a JSON object with `name`, `terrain`, and `climate` properties.
- `GET /planets/{id}`: Retrieve a planet by its ID.
- `GET /planets/name/{name}`: Retrieve a planet by its name.
- `GET /planets`: List all planets. You can filter the results by adding `terrain` or `climate` query parameters.
- `DELETE /planets/{id}`: Remove a planet by its ID.

## Running the Application

The project uses MySQL, so remember to create the database.

```
$ sudo mysql

CREATE USER 'user123'@'%' IDENTIFIED BY 'senha123';
GRANT ALL PRIVILEGES ON *.* TO 'user123'@'%' WITH GRANT OPTION;

exit

$ mysql -u user123 -p

CREATE DATABASE starwars;

exit
```

To run the application, you need to have Java and Maven installed on your machine. Then, you can run the following command in the root directory of the project:

```bash
mvn spring-boot:run
```

This will start the application on port 8080. You can then access the API at http://localhost:8080.  

## Testing

The application includes a comprehensive suite of integration tests to ensure its functionality. These tests are written using the Spring Boot Test framework, which provides utilities for testing Spring applications with JUnit.

The tests cover all the main features of the application, including:

- Creating a new planet
- Retrieving a planet by ID
- Retrieving a planet by name
- Listing all planets with optional filters
- Removing a planet by ID

Each test ensures that the API endpoints return the expected results when given valid input, and that they handle errors gracefully when given invalid input.

To run the tests, you need to have Java and Maven installed on your machine. You can run the tests with the following command:

```bash
mvn test
```

## Docker Usage

This application is also Dockerized for easy setup and deployment. Docker is a platform that allows you to develop, ship, and run applications inside containers. It provides a way to package up an application with all of the parts it needs, such as libraries and other dependencies, and ship it all out as one package.

### Building the Docker Image

To build the Docker image for this application, you can use the Dockerfile provided in the root directory of the project. Run the following command in the root directory:

```bash
docker build -t sw-planet-api .
```

This command builds a Docker image using the Dockerfile in the current directory and tags it as `sw-planet-api`.

### Running the Docker Container

After the image is built, you can run the application inside a Docker container with the following command:

```bash
docker run -p 8080:8080 sw-planet-api
```

This command starts a new Docker container from the `sw-planet-api` image and maps the container's port 8080 to port 8080 on your host machine. You can then access the API at `http://localhost:8080`.

### Docker Compose

If you're using Docker Compose to manage your application and its dependencies as a set of interconnected Docker containers, you can use the `docker-compose.yml` file provided in the root directory of the project. To start the application with Docker Compose, run the following command:

```bash
docker-compose up
```

This command starts all the services defined in `docker-compose.yml`. In this case, it starts the `sw-planet-api` application and any other services it depends on (like a database service).

Remember to stop the containers when you're done by running `docker-compose down`.

### Testing with Docker

You can also run the tests inside a Docker container. This ensures that the tests are run in the same environment as the application. To run the tests, you can modify the Dockerfile or create a new one specifically for testing. Then, you can build a Docker image for testing and run the tests inside a Docker container.

Please note that you need to have Docker installed on your machine to use these features. You can download Docker from the [official website](https://www.docker.com/).

## Contributing

If you want to contribute to this project, please open a new issue describing the change you want to make. You can then open a pull request linked to that issue.  

## 
License
This project is licensed under the MIT License.
