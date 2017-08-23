package com.corenlpanalyzer.api.Service;

import com.corenlpanalyzer.api.NLP.Entities.TopicExtractionResult;

public interface ITopicExtractionService {
    TopicExtractionResult getTopic(String text);
    TopicExtractionResult getTopic(String text, int numTopics);
    TopicExtractionResult getTopic(String text, int numTopics, int numWords);
}
