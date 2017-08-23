package com.corenlpanalyzer.api.NLP.Summarizer;

import com.corenlpanalyzer.api.NLP.Summarizer.summarizer.DocumentSummarizer;
import com.corenlpanalyzer.api.NLP.Summarizer.summarizer.KeywordExtractor;
import com.corenlpanalyzer.api.NLP.Summarizer.summarizer.SentencePreprocessor;
import com.corenlpanalyzer.api.NLP.Summarizer.summarizer.SentenceSegmenter;

public class Summarizer {
    private String text;
    private String summary;
    private String keywords;

    private int SUMMARY_PERCENTAGE = 5;

    private DocumentSummarizer docsum;
    private KeywordExtractor keyext;

    {
        SentenceSegmenter seg = new SentenceSegmenter();
        SentencePreprocessor prep = new SentencePreprocessor();
        docsum = new DocumentSummarizer(seg, prep);
        keyext = new KeywordExtractor(seg, prep);
    }

    public void summarize(){
        if (text != null){
            summary = docsum.summarize(text, SUMMARY_PERCENTAGE);
            keywords = keyext.extract(summary);
        }
    }

    public String getSummary() {
        if (summary != null){
            return summary;
        }
        if (text != null){
            return docsum.summarize(text, SUMMARY_PERCENTAGE);
        }
        return null;
    }

    public String getKeywords(){
        if (keywords != null){
            return keywords;
        } else if (summary != null){
            return keyext.extract(summary);
        } else if (text != null){
            summary = docsum.summarize(text, SUMMARY_PERCENTAGE);
            return keyext.extract(summary);
        }
        return null;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStringSummary(){
        return text;
    }

    public int getSUMMARY_PERCENTAGE() {
        return SUMMARY_PERCENTAGE;
    }

    public void setSUMMARY_PERCENTAGE(int SUMMARY_PERCENTAGE) {
        this.SUMMARY_PERCENTAGE = SUMMARY_PERCENTAGE;
    }

    public String getText() {
        return text;
    }
}
