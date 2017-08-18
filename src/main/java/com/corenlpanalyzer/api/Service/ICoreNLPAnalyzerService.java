package com.corenlpanalyzer.api.Service;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Domain.RawPageData;
import com.corenlpanalyzer.api.Runnables.ICoreNLPAnalyzer;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

/**
 * This is an interface which defines a service
 * for conducting text analysis with CoreNLP.
 */
public interface ICoreNLPAnalyzerService {
    /**
     * @param targetURL The link to the page to be analyzed.
     * @return An instance of AnalysisResult with analysis data.
     */
    default AnalysisResult score(RawPageData data) {
        return new AnalysisResult();
    }

    default AnalysisResult score(String rawText){
        return new AnalysisResult();
    }

    ICoreNLPAnalyzer getAnalyzer(String rawText);

    void pushAnalyzer(StanfordCoreNLP coreNLP);
}
