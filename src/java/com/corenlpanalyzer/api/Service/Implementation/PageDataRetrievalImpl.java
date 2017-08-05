package com.corenlpanalyzer.api.Service.Implementation;

import com.corenlpanalyzer.api.Domain.RawPageData;
import com.corenlpanalyzer.api.Service.IPageDataRetrievalService;
import org.springframework.stereotype.Service;

@Service
public class PageDataRetrievalImpl implements IPageDataRetrievalService {
    @Override
    public RawPageData getPageData(String targetURL) {
        return null;
    }
}
