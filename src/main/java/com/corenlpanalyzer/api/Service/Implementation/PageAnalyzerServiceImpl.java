package com.corenlpanalyzer.api.Service.Implementation;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Domain.PageAnalysisResult;
import com.corenlpanalyzer.api.Domain.RawPageData;
import com.corenlpanalyzer.api.Runnables.ICoreNLPAnalyzer;
import com.corenlpanalyzer.api.Service.ICoreNLPAnalyzerService;
import com.corenlpanalyzer.api.Service.IPageAnalyzerService;
import com.corenlpanalyzer.api.Service.IPageDataRetrievalService;
import com.corenlpanalyzer.api.Utils.RemoteAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class PageAnalyzerServiceImpl implements IPageAnalyzerService{
    private final IPageDataRetrievalService pageDataRetrievalService;
    private final ICoreNLPAnalyzerService coreNLPAnalyzerService;
    private ExecutorService executor;

    private int THREAD_NUM = 2;

    @Autowired
    public PageAnalyzerServiceImpl(IPageDataRetrievalService pageDataRetrievalService, ICoreNLPAnalyzerService coreNLPAnalyzerService) {
        this.pageDataRetrievalService = pageDataRetrievalService;
        this.coreNLPAnalyzerService = coreNLPAnalyzerService;
    }

    @Override
    public PageAnalysisResult score(String targetURL){
        RawPageData data;

        try {
            data = pageDataRetrievalService.getPageData(targetURL);
        } catch (RemoteAPIException ex){
            try {
                data = pageDataRetrievalService.getPageData(targetURL);
            } catch (RemoteAPIException e){
                return null;
            }
        }

        StringBuilder metaText = new StringBuilder();
        for(String k : data.getMetadata().values()){
            metaText.append(k).append(".\n");
        }

        StringBuilder wholePageText = new StringBuilder();
        wholePageText
                .append(data.getTitle())
                .append("\n")
                .append(metaText.toString())
                .append("\n")
                .append(data.getBodyText());


        PageAnalysisResult analysisResult = new PageAnalysisResult();

        String[] texts = {data.getTitle(), metaText.toString(), data.getBodyText(), wholePageText.toString()};
        ICoreNLPAnalyzer[] analyzers = new ICoreNLPAnalyzer[4]; //{metaAnalyzer, titleAnalyzer, bodyAnalyzer, wholeAnalyzer};
        AnalysisResult[] results = new AnalysisResult[4];

        executor = Executors.newFixedThreadPool(THREAD_NUM);

        for(int i = 0; i < 4; i++){
            analyzers[i] = coreNLPAnalyzerService.getAnalyzer(texts[i]);
            if (i == 2){
                analyzers[i].setUseLDA(true);
            }
            executor.execute(analyzers[i]);
        }
        executor.shutdown();
        while (!executor.isTerminated()){
            synchronized (this){
                try {
                    this.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        for(int i = 0; i < 4; i++){
            results[i] = analyzers[i].getResult();
        }

        analysisResult.setTitleAnalysisResult(results[0]);
        analysisResult.setMetaAnalysisResult(results[1]);
        analysisResult.setBodyAnalysisResult(results[2]);
        analysisResult.setWholePageAnalysisResult(results[3]);

        return analysisResult;
    }
}
