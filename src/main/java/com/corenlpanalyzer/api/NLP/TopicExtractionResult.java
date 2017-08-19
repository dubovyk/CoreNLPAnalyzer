package com.corenlpanalyzer.api.NLP;

import java.util.Arrays;

public class TopicExtractionResult {
    private int numTopics;
    private int numWords;

    private TopicWordTuple[][] data;
    private int[] sizes;

    public TopicExtractionResult() {
        this(5);
    }

    public TopicExtractionResult(int numTopics) {
        this(numTopics, 5);
    }

    public TopicExtractionResult(int numTopics, int numWords) {
        this.numTopics = numTopics;
        this.numWords = numWords;
        this.data = new TopicWordTuple[numTopics][numWords];
        this.sizes = new int[numTopics];
        for(int i = 0; i<numTopics; i++){
            sizes[i] = 0;
        }
    }


    public void addWordData(int topicNum, String word, double weight){
        if (sizes[topicNum] < this.numWords){
            this.data[topicNum][sizes[topicNum]] = new TopicWordTuple(word, weight);
            sizes[topicNum] += 1;
        }
    }

    public int getNumTopics() {
        return numTopics;
    }

    public int getNumWords() {
        return numWords;
    }

    public TopicWordTuple[][] getData() {
        return data;
    }

    public TopicWordTuple[] getTopicData(int topic){
        if (topic < numTopics){
            return this.data[topic];
        } else {
            return null;
        }
    }

    public String toString(){
        return null;
    }

    public String toHtmlString(){
        StringBuffer buffer = new StringBuffer();
        for(int topic = 0; topic < data.length; topic++){
            buffer.append("TOPIC ")
                    .append(topic)
                    .append("<br/><hr/>");
            for(int word = 0; word < data[topic].length; word++){
                buffer.append(data[topic][word].getWord())
                        .append(" : ")
                        .append(data[topic][word].getWeight())
                        .append("<br/>");
            }
            buffer.append("<br/>");
        }
        return buffer.toString();
    }

    public Object[][] toStringArray(){
        String[][] result = new String[numTopics][numWords];
        for(int i = 0; i < numTopics; i++){
            for(int j = 0; j < numWords; j++){
                result[i][j] = data[i][j].getWord();
            }
        }
        return result;
    }
}
