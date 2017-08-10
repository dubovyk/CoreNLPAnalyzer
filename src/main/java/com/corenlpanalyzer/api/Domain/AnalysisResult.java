package com.corenlpanalyzer.api.Domain;

public class AnalysisResult {
    private String targetText;
    private double bodyEmotionsCoefficient;
    private String parseTree;
    private int wordCount, sentenceCount;
    private float wordsPerSentence;

    public AnalysisResult() {
    }

    public AnalysisResult(String targetText, double bodyEmotionsCoefficient) {
        this.targetText = targetText;
        this.bodyEmotionsCoefficient = bodyEmotionsCoefficient;
        this.parseTree = new String ();
    }

    public String getParseTree() {
        return parseTree;
    }

    public void appendParseTree(String parseTree) {
        if (!parseTree.isEmpty()){
            this.parseTree += parseTree;
        } else {
            this.parseTree = parseTree;
        }
    }

    public void setParseTree(String parseTree) {
        this.parseTree = parseTree;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getSentenceCount() {
        return sentenceCount;
    }

    public void setSentenceCount(int sentenceCount) {
        this.sentenceCount = sentenceCount;
    }

    public float getWordsPerSentence() {
        return wordsPerSentence;
    }

    public void setWordsPerSentence(float wordsPerSentence) {
        this.wordsPerSentence = wordsPerSentence;
    }

    public String getTargetText() {
        return targetText;
    }

    public void setTargetText(String targetText) {
        this.targetText = targetText;
    }

    public double getBodyEmotionsCoefficient() {
        return bodyEmotionsCoefficient;
    }

    public void setBodyEmotionsCoefficient(double bodyEmotionsCoefficient) {
        this.bodyEmotionsCoefficient = bodyEmotionsCoefficient;
    }
}
