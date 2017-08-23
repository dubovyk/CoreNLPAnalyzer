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

The simplest way to use the application is to use its web-demo. To open it, go to the '/' address of your host with the analyzer. You`ll see a main page of an app, like on the image below.

![Main_interface](imgs/interface-01.PNG "Main interface of an app")

On this page, you can enter either a link to a page to be analyzed, either a text. If you`ll enter a text, then it will be analyzed on following aspects:
- Sentiment (from 1 to 5)
- Coreference chains
- LDA topic extraction (basically, 5 topics, 5 top words each)
- Named entities recognition (in web version just Person, Place, Organization and MISC, more via API)
- Short summary (up to 10 percents of text size)
- Keywords
- Basic statistics (number of words, sentences, words per sentence).

Also, application will build and return a parse tree of the text.

In case if user enters a link in the format of ```http://somesite.example/sompepage``` services will analyze such blocks of the page:
- Title
- Meta data
- Body text
- All previous together

However, it will do **topic extraction and summarization just for body** block.

## API reference

Another way to use the application is via API. It provides endpoints both for web-page analysis and single text processing.

### Web-page analysis
#### ```GET /url_analyze?target_url=http://somthing.example/```

To analyze a page, 