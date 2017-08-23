package com.corenlpanalyzer.api.Service.Implementation;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Domain.RawPageData;
import com.corenlpanalyzer.api.NLP.Runnables.ICoreNLPAnalyzer;
import com.corenlpanalyzer.api.NLP.Runnables.Implementation.CoreNLPAnalyzer;
import com.corenlpanalyzer.api.Service.ICoreNLPAnalyzerService;
import com.corenlpanalyzer.api.Service.IPageDataRetrievalService;
import com.corenlpanalyzer.api.Utils.CoreNLPAnalyzerPool;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is an implementation of ICoreNLPAnalyzerService which works with
 * remote Stanford CoreNLP server.
 */
@SuppressWarnings("Duplicates")
@Service
public class CoreNLPAnalyzerServiceImpl implements ICoreNLPAnalyzerService {
    private final IPageDataRetrievalService pageDataRetrievalService;
    private int THREAD_NUM = 2;

    @Autowired
    public CoreNLPAnalyzerServiceImpl(IPageDataRetrievalService pageDataRetrievalService) {
        this.pageDataRetrievalService = pageDataRetrievalService;
    }

    /**
     * This method analyzes a scraped web-page with a
     * method for plain-text analysis.
     *
     * @param data RawPageData instance for page to be analyzed.
     * @return An instance of AnalysisResult with analysis data.
     */
    @Override
    public AnalysisResult score(RawPageData data) {
        AnalysisResult result = score(data.getBodyText());
        return result;
    }


    @Override
    public AnalysisResult score(String rawText){
        System.out.println(String.format("Attempting to get annotator."));
        ICoreNLPAnalyzer analyzer = getAnalyzer(rawText);
        System.out.println(String.format("Got an annotator now."));
        analyzer.run();
        System.out.println(String.format("We have results."));
        AnalysisResult result = analyzer.getResult();
        pushAnalyzer(analyzer.getAnnotator());
        System.out.println(String.format("Pushed back annotator now."));
        return result;
    }

    @Override
    public ICoreNLPAnalyzer getAnalyzer(String rawText){
        ICoreNLPAnalyzer analyzer = new CoreNLPAnalyzer();
        analyzer.setRawText(rawText);
        return analyzer;
    }

    @Override
    public void pushAnalyzer(StanfordCoreNLP coreNLP){
        CoreNLPAnalyzerPool.getInstance().pushAnalyzer(coreNLP);
    }
}
