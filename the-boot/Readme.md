# The-Boot Spring Application
This is the demo spring boot application which has two versions. v0.1 supports H2 database and v1.0 supports MySql database as backend.
## Maven
Build the application and run the jar with command ``` java -jar <jar-name> ```
## Docker
Dockerfile is supplied in the project root folder. Build the project and navigate to project root folder to run the following command to build the docker image and run the application in docker container. 
Make sure that the jar file is created in the target folder.
Build the image from project root folder

```
docker image build -t the-boot .
```
Run the image in interactive mode mapping the port 8082 to 8080 in the outside. For the spring boot app, port mapping is done in the application.properties file in the resource folder.

```
docker run -p 8080:8082 -it the-boot
```
Tag and push the image to dockerhub (username should be dvkdoc)

```
docker tag the-boot dvkdoc/the-boot:1.0 
docker push dvkdoc/the-boot:1.0
```

Individually, mysql container can be started and linked to the spring boot app with below commands

```
docker run -d --env MYSQL_ROOT_PASSWORD=the-boot --env MYSQL_USER=the-boot --env MYSQL_PASSWORD=the-boot --env MYSQL_DATABASE=todos --name mysql --publish 3306:3306 mysql:5.7
docker run -p 8080:8082 --link=mysql --env BOOT_HOSTNAME=mysql -it the-boot
```

## Docker-compose
The application version 0.1 can be run only with docker but when the application becomes complex, it is difficult to manage multiple docker container separately. 
Run the spring boot app and mysql containers of version 1.0 from docker-compose.yaml file

```
docker-compose up -d #start in background
docker-compose down #stop the application
```
