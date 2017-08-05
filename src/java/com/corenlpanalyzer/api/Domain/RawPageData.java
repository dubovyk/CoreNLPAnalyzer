package com.corenlpanalyzer.api.Domain;

import java.util.Map;

/**
 * This class represents a data about a single
 * scraped page. It should contain all inforamtion
 * required for further analysis.
 */
public class RawPageData {
    private String url;
    private int status;

    private Map<String, String> result;

}
