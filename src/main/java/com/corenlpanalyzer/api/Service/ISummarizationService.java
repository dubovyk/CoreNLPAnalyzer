package com.corenlpanalyzer.api.Service;

public interface ISummarizationService {
    String getSummary(String text);
    String getTags(String text);
}
