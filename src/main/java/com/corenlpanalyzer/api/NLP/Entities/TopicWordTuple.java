package com.corenlpanalyzer.api.NLP.Entities;

public class TopicWordTuple {
    private String word;
    private double weight;

    public TopicWordTuple() {
    }

    public TopicWordTuple(String word, double weight) {
        this.word = word;
        this.weight = weight;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
