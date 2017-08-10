package com.corenlpanalyzer.api.Service;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Domain.RawPageData;

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
        System.out.println("Default one");
        return new AnalysisResult();
    }
}
