package com.corenlpanalyzer.api.Service;

import com.corenlpanalyzer.api.Domain.RawPageData;

/**
 * This interface defines a service which is
 * used to get raw page data via API endpoint "/rawscrape".
 */
public interface IPageDataRetrievalService {
    /**
     * @param targetURL A link to the page data about which is to be received
     * @return An instance of RawPageData with info about the page.
     */
    RawPageData getPageData(String targetURL);
}
