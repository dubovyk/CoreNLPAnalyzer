package com.corenlpanalyzer.api.Runnables;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public interface ICoreNLPAnalyzer extends Runnable {
    default AnalysisResult getResult(){return new AnalysisResult();}

    StanfordCoreNLP getAnnotator();

    void setRawText(String rawText);
    void setAnnotator(StanfordCoreNLP coreNLP);
}
