# Stanford CoreNLP web-page analysis tool

## Requirements

To build and run this application you`ll need to have following tools:
- Java 1.7+
- Docker
- Maven

## Build process

Firstly, you`ll need to build an app with basic maven command 
```mvn clean install```. Then, move resulted .jar file to the directory with a Docker file run.docker from this repository and build a  docker image with command ```sudo docker build -f run.docker -t corenlp/production:1 .``` , where corenlp/production:1 may be any image name you prefer. After that you can run an image with ``` sudo docker run -p 8080:8080 -p 80:8080 corenlp/production:1 ```. Also, this command will map 8080 and 80 ports to the app.

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

### Raw text full analysis
#### ```POST /raw_analyze```

To get analysis of raw text, you can use the above endpoint. The request, should be in the following format:

```
POST /raw_analyze HTTP/1.1
Host: yourhost
Content-Type: application/json
{
"text": ["Text 1.", "Text 2"]
}
```

As you can see, it takes an array of input texts and return an array of analysis results.

### Summarization
#### ```POST /single_analyze/summary```

You can also get just a summary and a list of keywords for given texts. To do it, you should call the ```/single_analysis/summary``` endpoint with the following format of request:
```json
POST /single_analyze/summary HTTP/1.1
Host: 127.0.0.1:8080
Content-Type: application/json

{
"text": ["This summary script works well for news articles and blog posts and that’s the basic motive of implementing this script.", "Some another text to be analyzed"]
}
```

### Topic extration
#### ```POST /single_analyze/topics```

Finally, you can get topics data of given texts or text. To do such analysis, you should use request, like the following one:
```json
POST /single_analyze/topics HTTP/1.1
Host: 127.0.0.1:8080
Content-Type: application/json
{
"text": ["This summary script works well for news articles and blog posts and that’s the basic motive of implementing this script. It inputs the text content, splits it into paragraphs, splits it into sentences, filter out stopwords, calculates score (relevance) of each sentence, and on the basis of the scores assigned to each sentence it displays the most relevant results depending upon how concise we want our summary to be."]
}
```

### Web-page analysis
#### GET /url_analyze?target_url=http://somthing.example/

To analyze a page, user should send a GET-request to the /url_analyze endpoint as in the example above. As a result, server will return a PageAnalysisResult object, like the following one:

```json
{
    "titleAnalysisResult": {
        "bodyEmotionsCoefficient": 4,
        "wordCount": 17,
        "sentenceCount": 1,
        "wordsPerSentence": 17,
        "corefChainsList": [
            [
                [
                    "1",
                    "California"
                ],
                [
                    "1",
                    "California 's"
                ]
            ]
        ],
        "topicExtractionResult": null,
        "summaryText": null,
        "keywords": null,
        "keywordsLength": 0,
        "nerentities": {
            "DATE": [],
            "DURATION": [],
            "LOCATION": [
                "California"
            ],
            "MISC": [],
            "NUMBER": [],
            "ORDINAL": [],
            "ORGANIZATION": [],
            "PERSON": [
                "Obi Kaufmann"
            ],
            "TIME": []
        }
    },
    "metaAnalysisResult": {
        "bodyEmotionsCoefficient": 3.2857142857142856,
        "wordCount": 194,
        "sentenceCount": 7,
        "wordsPerSentence": 27.714285,
        "corefChainsList": [
            [
                [
                    "1",
                    "california"
                ],
                [
                    "2",
                    "California"
                ],
                [
                    "2",
                    "California 's"
                ],
                [
                    "3",
                    "the richly illustrated California Field Atlas"
                ],
                [
                    "4",
                    "California"
                ],
                [
                    "4",
                    "California 's"
                ],
                [
                    "5",
                    "the richly illustrated California Field Atlas"
                ],
                [
                    "7",
                    "the richly illustrated California Field Atlas"
                ]
            ],
            [
                [
                    "2",
                    "Obi Kaufmann 's"
                ],
                [
                    "3",
                    "Obi Kaufmann"
                ],
                [
                    "4",
                    "Obi Kaufmann 's"
                ],
                [
                    "5",
                    "Obi Kaufmann"
                ],
                [
                    "7",
                    "Obi Kaufmann"
                ]
            ],
            [
                [
                    "3",
                    "the beautiful landscapes"
                ],
                [
                    "5",
                    "the beautiful landscapes"
                ],
                [
                    "7",
                    "the beautiful landscapes"
                ]
            ],
            [
                [
                    "3",
                    "Naturalist"
                ],
                [
                    "5",
                    "Naturalist"
                ],
                [
                    "7",
                    "Naturalist"
                ]
            ],
            [
                [
                    "3",
                    "our"
                ],
                [
                    "3",
                    "us"
                ],
                [
                    "5",
                    "our"
                ]
            ],
            [
                [
                    "3",
                    "the Golden State"
                ],
                [
                    "5",
                    "our fair state"
                ],
                [
                    "7",
                    "the Golden State"
                ]
            ],
            [
                [
                    "2",
                    "Atlas ' paints"
                ],
                [
                    "4",
                    "Atlas ' paints"
                ]
            ],
            [
                [
                    "3",
                    "our fair state.Kaufmann set out to uncover the secrets of the Golden State and provide us"
                ],
                [
                    "7",
                    "our fair state.Kaufmann set out to uncover the secrets of the Golden State and provide us"
                ]
            ],
            [
                [
                    "3",
                    "the secrets of the Golden State"
                ],
                [
                    "7",
                    "the secrets of the Golden State"
                ]
            ],
            [
                [
                    "2",
                    "Atlas '"
                ],
                [
                    "4",
                    "Atlas '"
                ]
            ],
            [
                [
                    "3",
                    "artist"
                ],
                [
                    "5",
                    "artist"
                ]
            ],
            [
                [
                    "3",
                    "activist"
                ],
                [
                    "5",
                    "activist"
                ]
            ],
            [
                [
                    "3",
                    "adventurer"
                ],
                [
                    "5",
                    "adventurer"
                ]
            ]
        ],
        "topicExtractionResult": null,
        "summaryText": null,
        "keywords": null,
        "keywordsLength": 0,
        "nerentities": {
            "DATE": [
                "now"
            ],
            "DURATION": [],
            "LOCATION": [
                "california",
                "California",
                "Field Atlas",
                "Golden State"
            ],
            "MISC": [
                "Naturalist"
            ],
            "NUMBER": [],
            "ORDINAL": [],
            "ORGANIZATION": [],
            "PERSON": [
                "Obi Kaufmann"
            ],
            "TIME": []
        }
    },
    "bodyAnalysisResult": {
        "bodyEmotionsCoefficient": 3.1578947368421053,
        "wordCount": 517,
        "sentenceCount": 19,
        "wordsPerSentence": 27.210526,
        "corefChainsList": [
            [
                [
                    "1",
                    "Kaufmann"
                ],
                [
                    "1",
                    "his"
                ],
                [
                    "2",
                    "he"
                ],
                [
                    "2",
                    "his"
                ],
                [
                    "2",
                    "he"
                ],
                [
                    "3",
                    "I"
                ],
                [
                    "3",
                    "I"
                ],
                [
                    "4",
                    "Kaufmann"
                ],
                [
                    "4",
                    "his"
                ],
                [
                    "7",
                    "he"
                ],
                [
                    "8",
                    "him"
                ],
                [
                    "8",
                    "his"
                ],
                [
                    "10",
                    "Kaufmann"
                ],
                [
                    "11",
                    "Kaufmann"
                ],
                [
                    "17",
                    "Kaufmann , who can be found working at his storefront"
                ],
                [
                    "19",
                    "Obi Kaufmann 's"
                ]
            ],
            [
                [
                    "8",
                    "California"
                ],
                [
                    "10",
                    "California"
                ],
                [
                    "11",
                    "California"
                ],
                [
                    "12",
                    "California"
                ],
                [
                    "13",
                    "California"
                ],
                [
                    "14",
                    "California"
                ],
                [
                    "14",
                    "it"
                ],
                [
                    "15",
                    "California"
                ],
                [
                    "18",
                    "California"
                ],
                [
                    "19",
                    "California"
                ]
            ],
            [
                [
                    "13",
                    "we"
                ],
                [
                    "13",
                    "our"
                ],
                [
                    "14",
                    "us"
                ],
                [
                    "14",
                    "we"
                ],
                [
                    "16",
                    "our"
                ]
            ],
            [
                [
                    "16",
                    "his"
                ],
                [
                    "17",
                    "me"
                ],
                [
                    "17",
                    "my"
                ],
                [
                    "17",
                    "his"
                ]
            ],
            [
                [
                    "10",
                    "this"
                ],
                [
                    "10",
                    "it"
                ],
                [
                    "10",
                    "its"
                ]
            ],
            [
                [
                    "13",
                    "This whole book"
                ],
                [
                    "16",
                    "his book"
                ],
                [
                    "19",
                    "Obi Kaufmann 's new book"
                ]
            ],
            [
                [
                    "3",
                    "the animals"
                ],
                [
                    "3",
                    "them"
                ],
                [
                    "7",
                    "the animals"
                ]
            ],
            [
                [
                    "13",
                    "the next 100 years"
                ],
                [
                    "13",
                    "the next 10,000 years"
                ]
            ],
            [
                [
                    "5",
                    "I"
                ],
                [
                    "5",
                    "my"
                ]
            ],
            [
                [
                    "5",
                    "It"
                ],
                [
                    "5",
                    "it"
                ]
            ],
            [
                [
                    "17",
                    "the High Sierra"
                ],
                [
                    "17",
                    "it"
                ]
            ],
            [
                [
                    "10",
                    "you"
                ],
                [
                    "10",
                    "you"
                ]
            ],
            [
                [
                    "11",
                    "the paintings and maps"
                ],
                [
                    "11",
                    "they"
                ]
            ],
            [
                [
                    "1",
                    "an artist 's"
                ],
                [
                    "15",
                    "the artist 's"
                ]
            ],
            [
                [
                    "1",
                    "the Golden State"
                ],
                [
                    "11",
                    "the state of the wilderness"
                ]
            ],
            [
                [
                    "6",
                    "the California Field Atlas"
                ],
                [
                    "19",
                    "The California Field Atlas"
                ]
            ],
            [
                [
                    "11",
                    "the wilderness"
                ],
                [
                    "16",
                    "the wilderness"
                ]
            ],
            [
                [
                    "9",
                    "almost a topographic relevance to the well-rendered animal"
                ],
                [
                    "9",
                    "it"
                ]
            ]
        ],
        "topicExtractionResult": {
            "numTopics": 5,
            "numWords": 5,
            "data": [
                [
                    {
                        "word": "california",
                        "weight": 10
                    },
                    {
                        "word": "paintings",
                        "weight": 2
                    },
                    {
                        "word": "artist's",
                        "weight": 2
                    },
                    {
                        "word": "obi",
                        "weight": 1
                    },
                    {
                        "word": "illustrations",
                        "weight": 1
                    }
                ],
                [
                    {
                        "word": "field",
                        "weight": 3
                    },
                    {
                        "word": "care",
                        "weight": 2
                    },
                    {
                        "word": "lost",
                        "weight": 2
                    },
                    {
                        "word": "real",
                        "weight": 2
                    },
                    {
                        "word": "state",
                        "weight": 2
                    }
                ],
                [
                    {
                        "word": "kaufmann",
                        "weight": 5
                    },
                    {
                        "word": "animals",
                        "weight": 2
                    },
                    {
                        "word": "kaufmann's",
                        "weight": 1
                    },
                    {
                        "word": "preorders",
                        "weight": 1
                    },
                    {
                        "word": "broadway",
                        "weight": 1
                    }
                ],
                [
                    {
                        "word": "years",
                        "weight": 2
                    },
                    {
                        "word": "natural",
                        "weight": 2
                    },
                    {
                        "word": "atlas",
                        "weight": 2
                    },
                    {
                        "word": "maps",
                        "weight": 2
                    },
                    {
                        "word": "beautiful",
                        "weight": 1
                    }
                ],
                [
                    {
                        "word": "book",
                        "weight": 3
                    },
                    {
                        "word": "wilderness",
                        "weight": 2
                    },
                    {
                        "word": "landscape",
                        "weight": 2
                    },
                    {
                        "word": "nature",
                        "weight": 2
                    },
                    {
                        "word": "oakland",
                        "weight": 1
                    }
                ]
            ]
        },
        "summaryText": "Kaufmann set out to uncover the secrets of the Golden State and provide us an artist's and poet's rendering of his best finds. ",
        "keywords": [
            "uncover",
            "state",
            "set",
            "secrets",
            "rendering",
            "provide",
            "poet",
            "kaufmann",
            "golden",
            "finds",
            "artist"
        ],
        "keywordsLength": 11,
        "nerentities": {
            "DATE": [
                "Future",
                "now"
            ],
            "DURATION": [
                "the next 100 years",
                "the next 10,000 years",
                "days"
            ],
            "LOCATION": [
                "Golden State",
                "Bay Area",
                "Mt. Diablo",
                "California Field Atlas",
                "California",
                "Yosemite",
                "Broadway",
                "Oakland"
            ],
            "MISC": [
                "Californian"
            ],
            "NUMBER": [
                "250",
                "600",
                "4130"
            ],
            "ORDINAL": [],
            "ORGANIZATION": [
                "Premium Arts"
            ],
            "PERSON": [
                "Kaufmann",
                "Obi"
            ],
            "TIME": []
        }
    },
    "wholePageAnalysisResult": {
        "bodyEmotionsCoefficient": 3.230769230769231,
        "wordCount": 729,
        "sentenceCount": 26,
        "wordsPerSentence": 28.038462,
        "corefChainsList": [
            [
                [
                    "1",
                    "California"
                ],
                [
                    "1",
                    "California 's"
                ],
                [
                    "1",
                    "california"
                ],
                [
                    "2",
                    "California"
                ],
                [
                    "2",
                    "California 's"
                ],
                [
                    "3",
                    "the richly illustrated California Field Atlas"
                ],
                [
                    "4",
                    "California"
                ],
                [
                    "4",
                    "California 's"
                ],
                [
                    "5",
                    "the richly illustrated California Field Atlas"
                ],
                [
                    "7",
                    "the richly illustrated California Field Atlas"
                ],
                [
                    "13",
                    "the California Field Atlas"
                ],
                [
                    "15",
                    "California"
                ],
                [
                    "17",
                    "California"
                ],
                [
                    "18",
                    "California"
                ],
                [
                    "19",
                    "California"
                ],
                [
                    "20",
                    "California"
                ],
                [
                    "21",
                    "California"
                ],
                [
                    "21",
                    "it"
                ],
                [
                    "22",
                    "California"
                ],
                [
                    "25",
                    "California"
                ],
                [
                    "26",
                    "The California Field Atlas"
                ],
                [
                    "26",
                    "California"
                ]
            ],
            [
                [
                    "1",
                    "Obi Kaufmann 's"
                ],
                [
                    "2",
                    "Obi Kaufmann 's"
                ],
                [
                    "3",
                    "Obi Kaufmann"
                ],
                [
                    "4",
                    "Obi Kaufmann 's"
                ],
                [
                    "5",
                    "Obi Kaufmann"
                ],
                [
                    "7",
                    "Obi Kaufmann"
                ],
                [
                    "8",
                    "Kaufmann"
                ],
                [
                    "11",
                    "Kaufmann"
                ],
                [
                    "11",
                    "his"
                ],
                [
                    "17",
                    "Kaufmann"
                ],
                [
                    "18",
                    "Kaufmann"
                ],
                [
                    "24",
                    "Kaufmann , who can be found working at his storefront"
                ],
                [
                    "26",
                    "Obi Kaufmann 's"
                ]
            ],
            [
                [
                    "8",
                    "poet 's"
                ],
                [
                    "8",
                    "his"
                ],
                [
                    "9",
                    "he"
                ],
                [
                    "9",
                    "his"
                ],
                [
                    "9",
                    "he"
                ],
                [
                    "10",
                    "I"
                ],
                [
                    "10",
                    "I"
                ],
                [
                    "14",
                    "he"
                ],
                [
                    "15",
                    "him"
                ],
                [
                    "15",
                    "his"
                ]
            ],
            [
                [
                    "3",
                    "our"
                ],
                [
                    "3",
                    "us"
                ],
                [
                    "5",
                    "our"
                ],
                [
                    "7",
                    "our"
                ],
                [
                    "7",
                    "us"
                ],
                [
                    "8",
                    "us"
                ]
            ],
            [
                [
                    "20",
                    "we"
                ],
                [
                    "20",
                    "our"
                ],
                [
                    "21",
                    "us"
                ],
                [
                    "21",
                    "we"
                ],
                [
                    "23",
                    "our"
                ]
            ],
            [
                [
                    "3",
                    "the Golden State"
                ],
                [
                    "5",
                    "our fair state"
                ],
                [
                    "7",
                    "the Golden State"
                ],
                [
                    "8",
                    "the Golden State"
                ],
                [
                    "18",
                    "the state of the wilderness"
                ]
            ],
            [
                [
                    "23",
                    "his"
                ],
                [
                    "24",
                    "me"
                ],
                [
                    "24",
                    "my"
                ],
                [
                    "24",
                    "his"
                ]
            ],
            [
                [
                    "3",
                    "the beautiful landscapes"
                ],
                [
                    "5",
                    "the beautiful landscapes"
                ],
                [
                    "7",
                    "the beautiful landscapes"
                ]
            ],
            [
                [
                    "3",
                    "the secrets of the Golden State"
                ],
                [
                    "7",
                    "the secrets of the Golden State"
                ],
                [
                    "8",
                    "the secrets of the Golden State"
                ]
            ],
            [
                [
                    "17",
                    "this"
                ],
                [
                    "17",
                    "it"
                ],
                [
                    "17",
                    "its"
                ]
            ],
            [
                [
                    "20",
                    "This whole book"
                ],
                [
                    "23",
                    "his book"
                ],
                [
                    "26",
                    "Obi Kaufmann 's new book"
                ]
            ],
            [
                [
                    "10",
                    "the animals"
                ],
                [
                    "10",
                    "them"
                ],
                [
                    "14",
                    "the animals"
                ]
            ],
            [
                [
                    "1",
                    "Atlas '"
                ],
                [
                    "2",
                    "Atlas '"
                ],
                [
                    "4",
                    "Atlas '"
                ]
            ],
            [
                [
                    "8",
                    "an artist 's"
                ],
                [
                    "22",
                    "the artist 's"
                ]
            ],
            [
                [
                    "16",
                    "almost a topographic relevance to the well-rendered animal"
                ],
                [
                    "16",
                    "it"
                ]
            ],
            [
                [
                    "18",
                    "the wilderness"
                ],
                [
                    "23",
                    "the wilderness"
                ]
            ],
            [
                [
                    "3",
                    "our fair state.Kaufmann set out to uncover the secrets of the Golden State and provide us"
                ],
                [
                    "7",
                    "our fair state.Kaufmann set out to uncover the secrets of the Golden State and provide us"
                ]
            ],
            [
                [
                    "1",
                    "premium arts"
                ],
                [
                    "24",
                    "Premium Arts"
                ]
            ],
            [
                [
                    "24",
                    "the High Sierra"
                ],
                [
                    "24",
                    "it"
                ]
            ],
            [
                [
                    "17",
                    "you"
                ],
                [
                    "17",
                    "you"
                ]
            ],
            [
                [
                    "18",
                    "the paintings and maps"
                ],
                [
                    "18",
                    "they"
                ]
            ],
            [
                [
                    "2",
                    "Atlas ' paints"
                ],
                [
                    "4",
                    "Atlas ' paints"
                ]
            ],
            [
                [
                    "20",
                    "the next 100 years"
                ],
                [
                    "20",
                    "the next 10,000 years"
                ]
            ],
            [
                [
                    "12",
                    "I"
                ],
                [
                    "12",
                    "my"
                ]
            ],
            [
                [
                    "12",
                    "It"
                ],
                [
                    "12",
                    "it"
                ]
            ],
            [
                [
                    "3",
                    "activist"
                ],
                [
                    "5",
                    "activist"
                ]
            ],
            [
                [
                    "3",
                    "adventurer"
                ],
                [
                    "5",
                    "adventurer"
                ]
            ]
        ],
        "topicExtractionResult": null,
        "summaryText": null,
        "keywords": null,
        "keywordsLength": 0,
        "nerentities": {
            "DATE": [
                "now",
                "Future"
            ],
            "DURATION": [
                "the next 100 years",
                "the next 10,000 years",
                "days"
            ],
            "LOCATION": [
                "California",
                "california",
                "Field Atlas",
                "Golden State",
                "Bay Area",
                "Mt. Diablo",
                "Yosemite",
                "Broadway",
                "Oakland"
            ],
            "MISC": [
                "Californian"
            ],
            "NUMBER": [
                "250",
                "600",
                "4130"
            ],
            "ORDINAL": [],
            "ORGANIZATION": [
                "Premium Arts"
            ],
            "PERSON": [
                "Obi Kaufmann",
                "Kaufmann",
                "Obi"
            ],
            "TIME": []
        }
    }
}
```
