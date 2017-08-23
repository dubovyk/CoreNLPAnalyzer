# Stanford CoreNLP web-page analysis tool

## Requirements

To build and run this application you`ll need to have following tools:
- Java 1.7+
- Docker
- Maven

## Build process

Firstly, you`ll need to build an app with basic maven command 
```mvn clean install```. Then, move resulted .jar file to the directory with a Docker file run.docker from this repository and build a docker image with command ```sudo docker build -f run.docker -t corenlp/production:1 .```, where corenlp/production:1 may be any image name you prefer. After that you can run an image with ```sudo docker run -p 8080:8080 -p 80:8080 corenlp/production:1```. Also, this command will map 8080 and 80 ports to the app.

To verify, that you`ve run application successfully, you can use a GET request to the following address: ```http://yourhost.example/echo```. It should return current date/time data if everything worked well.

## Web usage

The simplest way to use the application is to use its web-demo. To open it, go to the '/' address of your host with the analyzer. You`ll see a main page of an app, like in image below.

Inline-style: 
![Kiku](imgs/interface-01.PNG)

## API reference