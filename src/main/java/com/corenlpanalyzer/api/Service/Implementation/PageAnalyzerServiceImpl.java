package com.corenlpanalyzer.api.Service.Implementation;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Domain.RawPageData;
import com.corenlpanalyzer.api.Service.ICoreNLPAnalyzerService;
import com.corenlpanalyzer.api.Service.IPageAnalyzerService;
import com.corenlpanalyzer.api.Service.IPageDataRetrievalService;
import com.corenlpanalyzer.api.Utils.RemoteAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageAnalyzerServiceImpl implements IPageAnalyzerService{
    private final IPageDataRetrievalService pageDataRetrievalService;
    private final ICoreNLPAnalyzerService coreNLPAnalyzerService;

    @Autowired
    public PageAnalyzerServiceImpl(IPageDataRetrievalService pageDataRetrievalService, ICoreNLPAnalyzerService coreNLPAnalyzerService) {
        this.pageDataRetrievalService = pageDataRetrievalService;
        this.coreNLPAnalyzerService = coreNLPAnalyzerService;
    }

    @Override
    public AnalysisResult score(String targetURL) {
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

        AnalysisResult result = coreNLPAnalyzerService.score(data);

        return result;
    }
}
