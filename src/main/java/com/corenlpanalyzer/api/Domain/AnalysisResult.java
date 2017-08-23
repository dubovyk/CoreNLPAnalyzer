package com.corenlpanalyzer.api.Domain;

import com.corenlpanalyzer.api.NLP.Entities.TopicExtractionResult;
import edu.stanford.nlp.coref.data.CorefChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnalysisResult{
    private String targetText;
    private double bodyEmotionsCoefficient;
    private String parseTree;
    private int wordCount, sentenceCount;
    private float wordsPerSentence;
    private List<CorefChain> corefChains;
    private Map<String, List<String>> NERentities;
    private List<List<String[]>> corefChainsList;
    private TopicExtractionResult topicExtractionResult;
    private String summaryText;
    private String keywordsString;

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

    public Map<String, List<String>> getNERentities() {
        return NERentities;
    }

    public void setNERentities(Map<String, List<String>> NERentities) {
        this.NERentities = NERentities;
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

    private List<CorefChain> getCorefChains() {
        return corefChains;
    }

    public void setCorefChains(List<CorefChain> corefChains) {
        this.corefChains = corefChains;
    }

    public void setCorefChainsList(List<List<String[]>> corefChainsList) {
        this.corefChainsList = corefChainsList;
    }

    public TopicExtractionResult getTopicExtractionResult() {
        return topicExtractionResult;
    }

    public void setTopicExtractionResult(TopicExtractionResult topicExtractionResult) {
        this.topicExtractionResult = topicExtractionResult;
    }

    public String getSummaryText() {
        return summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }

    public String getKeywordsString() {
        return keywordsString;
    }

    public void setKeywordsString(String keywordsString) {
        this.keywordsString = keywordsString;
    }

    public String corefChainsListToString(){
        StringBuilder builder = new StringBuilder();
        for(CorefChain c : corefChains) {
            builder.append(c).append("<br>");
        }
        return builder.toString();
    }

    //TODO: change to enum
    public String getNamedEntititesAsString(String type){
        if (NERentities.get(type) == null){
            return "";
        }
        List<String> entities = NERentities.get(type);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < entities.size(); i++){
            buffer.append(entities.get(i));
            if (i < entities.size() - 1){
                buffer.append(", ");
            }
        }
        return buffer.toString();
    }

    public List<List<String[]>> getCorefChainsList() {
        List<List<String[]>> result = new ArrayList<>();
        for(CorefChain c : corefChains) {
            List<String[]> chain = new ArrayList<>();
            for (CorefChain.CorefMention mention: c.getMentionsInTextualOrder()){
                String[] arr = new String[2];
                arr[0] = String.valueOf(mention.sentNum);
                arr[1] = mention.mentionSpan;
                chain.add(arr);
            }
            result.add(chain);
        }
        return result;
    }
}
