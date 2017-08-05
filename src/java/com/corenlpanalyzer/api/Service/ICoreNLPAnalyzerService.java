package com.corenlpanalyzer.api.Service;

import com.corenlpanalyzer.api.Domain.AnalysisResult;

/**
 * This is an interface which defines a service
 * for conducting text analysis with CoreNLP.
 */
public interface ICoreNLPAnalyzerService {
    /**
     * @param targetURL The link to the page to be analyzed.
     * @return An instance of AnalysisResult with analysis data.
     */
    default AnalysisResult score(String targetURL) {
        return new AnalysisResult();
    }
}
