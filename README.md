# QR Code API
This template is designed to generate QR codes of various types, such as vCard, text, event, wifi, and more.

## Using this Template

### Initialization
To use this project, follow one of the following options:

### Download the project as a compressed .zip file
1. Download the project in .zip format from the "Code" option in this repository.
2. Once downloaded, unzip the file and open the project folder.
3. To run the application, use the command: 
    ```
    mvn spring-boot:run  
    ```
This command will start the application using Maven and Spring Boot. Once the application is running, you can begin using it.


### Clone the repository
1. Clone the Git repository on your PC with the command:
    ```
    git@github.com:EdwinC27/qr-code.git
    ```
2. Once downloaded, open the project folder.
3. To run the application, use the command:
    ```
    mvn spring-boot:run  
    ```
This command will start the application using Maven and Spring Boot. Once the application is running, you can begin using it.

### Docker
1. Download the `docker-compose.yml` file from the <a href="https://github.com/EdwinC27/qr-code/tree/api/dockerDir">dockerDir</a> folder
2. Open a terminal and navigate to the location where the `docker-compose.yml` file is stored.
3. Execute the following command in the terminal:
    ```
    docker-compose up -d 
    ```
This command will start the Docker containers as defined in the `docker-compose.yml` file in the background. Wait a few seconds until the containers are ready for use.


## Requirements:
- To use this program, it's necessary for your operating system to have Java 11 or a more recent version installed. If you don't have Java, you can download and install the corresponding version from the [Adoptium](https://adoptium.net/temurin/releases/) website.

### API Documentation
You can explore the API documentation using the following URLs:

- [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs): to access the OpenAPI specification in JSON format.
- [http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config): to access the Swagger UI user interface.

Exposed Ports:
- Port: 8080
- Context: /api
- URL: [http://localhost:8080/api](http://localhost:8080/api)
