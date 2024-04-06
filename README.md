# Shopping App

This repository contains the source code for a Shopping App, implemented using Spring Boot.

## Prerequisites

Before running the application, make sure you have the following installed:

- Java JDK
- Maven
- MySQL (or any other preferred database)

## Using EclipseIDE 
1. Clone repository
2. goto file section and click on the import.
3. then search for maven then click on the existing maver project then next and submit.
4. configure application.properteis for DB connection.
5. run the spring Boot appliacation.
6. Test the api using PostMan use this link to get API documentation
7. Api Documentaion link [click Here](https://cloudy-station-823286.postman.co/workspace/New-Team-Workspace~e0b5b844-70e8-43d8-a4fd-34574e97ebf0/collection/30934677-e43c6270-c604-4be4-989c-1290f9fc764d?action=share&creator=30934677)

## Setup

1. Clone the repository to your local machine:

```bash
git clone <repository_url>
```
2. Navigate to the project directory:
   
```bash
cd shoppindapp
```

3. Update the application.properties file in the src/main/resources directory with your database configuration:
   
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/shoppingdb
spring.datasource.username=<your_database_username>
spring.datasource.password=<your_database_password>
```

4. Build the project using Maven:
   
```bash
mvn clean package
```
 
6. Run the application:

```bash
java -jar target/shoppingapp-<version>.jar
```

## Contributing

- If you'd like to contribute to this project, please fork the repository, make your changes, and submit a pull request. Your contributions are always welcome!
## License
- This project is licensed under the MIT License - see the LICENSE file for details.
```lua
This format allows users to easily follow the instructions and copy the bash commands for running the project.
```
