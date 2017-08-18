package com.corenlpanalyzer.api.Service.Implementation;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Domain.PageAnalysisResult;
import com.corenlpanalyzer.api.Domain.RawPageData;
import com.corenlpanalyzer.api.Runnables.ICoreNLPAnalyzer;
import com.corenlpanalyzer.api.Runnables.Implementation.CoreNLPAnalyzer;
import com.corenlpanalyzer.api.Service.ICoreNLPAnalyzerService;
import com.corenlpanalyzer.api.Service.IPageAnalyzerService;
import com.corenlpanalyzer.api.Service.IPageDataRetrievalService;
import com.corenlpanalyzer.api.Utils.RemoteAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("Duplicates")
@Service
public class PageAnalyzerServiceImpl implements IPageAnalyzerService{
    private final IPageDataRetrievalService pageDataRetrievalService;
    private final ICoreNLPAnalyzerService coreNLPAnalyzerService;
    private final ExecutorService executor;

    private int THREAD_NUM = 2;

    @Autowired
    public PageAnalyzerServiceImpl(IPageDataRetrievalService pageDataRetrievalService, ICoreNLPAnalyzerService coreNLPAnalyzerService) {
        this.pageDataRetrievalService = pageDataRetrievalService;
        this.coreNLPAnalyzerService = coreNLPAnalyzerService;
        executor = Executors.newFixedThreadPool(THREAD_NUM);
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

        ICoreNLPAnalyzer metaAnalyzer = coreNLPAnalyzerService.getAnalyzer(metaText.toString());
        ICoreNLPAnalyzer titleAnalyzer = coreNLPAnalyzerService.getAnalyzer(data.getTitle());
        ICoreNLPAnalyzer bodyAnalyzer = coreNLPAnalyzerService.getAnalyzer(data.getBodyText());
        ICoreNLPAnalyzer wholeAnalyzer = coreNLPAnalyzerService.getAnalyzer(wholePageText.toString());

        String[] texts = {data.getTitle(), metaText.toString(), data.getBodyText(), wholePageText.toString()};
        ICoreNLPAnalyzer[] analyzers = {metaAnalyzer, titleAnalyzer, bodyAnalyzer, wholeAnalyzer};
        AnalysisResult[] results = new AnalysisResult[4];

        for(int i = 0; i < 4; i++){
            analyzers[i] = coreNLPAnalyzerService.getAnalyzer(texts[i]);
            executor.execute(analyzers[i]);
        }
        executor.shutdown();
        while (!executor.isTerminated()){

        }

        for(int i = 0; i < 4; i++){
            results[i] = analyzers[i].getResult();
        }

        analysisResult.setTitleAnalysisResult(results[0]);
        analysisResult.setMetaAnalysisResult(results[1]);
        analysisResult.setBodyAnalysisResult(results[2]);
        analysisResult.setWholePageAnalysisResult(results[3]);

        /*ICoreNLPAnalyzer metaAnalyzer = coreNLPAnalyzerService.getAnalyzer(metaText.toString());
        ICoreNLPAnalyzer titleAnalyzer = coreNLPAnalyzerService.getAnalyzer(data.getTitle());
        Thread metaAnalyzerThread = new Thread(metaAnalyzer);
        Thread titleAnalyzerThread = new Thread(titleAnalyzer);
        metaAnalyzerThread.start();
        titleAnalyzerThread.start();
        try {
            metaAnalyzerThread.join();
            titleAnalyzerThread.join();
        } catch (InterruptedException ignored){

        } finally {
            coreNLPAnalyzerService.pushAnalyzer(metaAnalyzer.getAnnotator());
            coreNLPAnalyzerService.pushAnalyzer(titleAnalyzer.getAnnotator());
        }

        analysisResult.setTitleAnalysisResult(titleAnalyzer.getResult());
        analysisResult.setMetaAnalysisResult(metaAnalyzer.getResult());

        ICoreNLPAnalyzer bodyAnalyzer = coreNLPAnalyzerService.getAnalyzer(data.getBodyText());
        ICoreNLPAnalyzer wholeAnalyzer = coreNLPAnalyzerService.getAnalyzer(wholePageText.toString());

        Thread bodyAnalyzerThread = new Thread(bodyAnalyzer);
        Thread wholeAnalyzerThread = new Thread(wholeAnalyzer);

        bodyAnalyzerThread.start();
        wholeAnalyzerThread.start();
        try {
            bodyAnalyzerThread.join();
            wholeAnalyzerThread.join();
        } catch (InterruptedException ignored){

        } finally {
            coreNLPAnalyzerService.pushAnalyzer(bodyAnalyzer.getAnnotator());
            coreNLPAnalyzerService.pushAnalyzer(wholeAnalyzer.getAnnotator());
        }

        analysisResult.setBodyAnalysisResult(bodyAnalyzer.getResult());
        analysisResult.setWholePageAnalysisResult(wholeAnalyzer.getResult());*/

        return analysisResult;
    }
}
