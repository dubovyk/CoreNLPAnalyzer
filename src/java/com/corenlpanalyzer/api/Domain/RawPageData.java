package com.corenlpanalyzer.api.Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a data about a single
 * scraped page. It should contain all inforamtion
 * required for further analysis.
 */
public class RawPageData {
    private String url;
    private String status;
    private String bodyText;
    private String title;
    private String titleImageLink;
    private Map<String, String> metadata;
    private Map<String, String> headers;
    private List<String> links;
    private String website;

    public RawPageData(){
        metadata = new HashMap<>();
        headers = new HashMap<>();
        links = new ArrayList<>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleImageLink() {
        return titleImageLink;
    }

    public void setTitleImageLink(String titleImageLink) {
        this.titleImageLink = titleImageLink;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public void addMetadata(String key, String value){
        metadata.put(key, value);
    }

    public void addHeader(String key, String value){
        headers.put(key, value);
    }

    public void addLink(String url){
        links.add(url);
    }
}
