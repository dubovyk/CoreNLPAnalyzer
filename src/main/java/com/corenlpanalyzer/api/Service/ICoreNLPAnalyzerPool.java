package com.corenlpanalyzer.api.Service;

import com.corenlpanalyzer.api.NLP.Runnables.ICoreNLPAnalyzer;

public interface ICoreNLPAnalyzerPool {
    ICoreNLPAnalyzer getAnalyzer(String rawText);
}
