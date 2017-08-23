package com.corenlpanalyzer.api.Service.Implementation;

import com.corenlpanalyzer.api.NLP.Summarizer.Summarizer;
import com.corenlpanalyzer.api.Service.ISummarizationService;

public class SummarizationServiceImpl implements ISummarizationService {
    private Summarizer summarizer;

    {
        summarizer = new Summarizer();
    }

    public String getSummary(String text){
        if (!(summarizer.getText() != null && summarizer.getText().equals(text))){
            summarizer.setText(text);
        }
        return summarizer.getSummary();
    }
    public String getTags(String text){
        if (!(summarizer.getText() != null && summarizer.getText().equals(text))){
            summarizer.setText(text);
        }
        return summarizer.getKeywords();
    }
}