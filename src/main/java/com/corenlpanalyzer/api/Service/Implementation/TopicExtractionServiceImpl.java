package com.corenlpanalyzer.api.Service.Implementation;

import com.corenlpanalyzer.api.NLP.Algorithm.TopicAnalyzer;
import com.corenlpanalyzer.api.NLP.Entities.TopicExtractionResult;
import com.corenlpanalyzer.api.Service.ITopicExtractionService;
import org.springframework.stereotype.Service;

@Service
public class TopicExtractionServiceImpl implements ITopicExtractionService{
    private static int DEFAULT_NUM_WORDS = 5;
    private static int DEFAULT_NUM_TOPICS = 5;

    public TopicExtractionResult getTopic(String text){
        return getTopic(text, DEFAULT_NUM_TOPICS);
    }

    public TopicExtractionResult getTopic(String text, int numTopics){
        return getTopic(text, numTopics, DEFAULT_NUM_WORDS);
    }


    public TopicExtractionResult getTopic(String text, int numTopics, int numWords){
        TopicAnalyzer topicAnalyzer = new TopicAnalyzer();
        try {
            return topicAnalyzer.analyze(text, numTopics, numWords);
        } catch (Exception ex){
            ex.printStackTrace();
            return  null;
        }
    }
}
