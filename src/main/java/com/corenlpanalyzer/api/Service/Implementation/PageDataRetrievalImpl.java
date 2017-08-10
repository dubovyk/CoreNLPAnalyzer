package com.corenlpanalyzer.api.Service.Implementation;

import com.corenlpanalyzer.api.Domain.RawPageData;
import com.corenlpanalyzer.api.Service.IPageDataRetrievalService;
import com.corenlpanalyzer.api.Utils.RemoteAPIException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PageDataRetrievalImpl implements IPageDataRetrievalService {
    /**
     * @param targetURL A link to the page to be scraped.
     * @return A string of the response body.
     * @throws RemoteAPIException This exception is thrown if response status differs from
     * 200.
     */
    private String downloadPage(String targetURL) throws RemoteAPIException{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("url", targetURL);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://54.70.208.36:8080/rawscrape", request, String.class);
        if (responseEntity.getStatusCodeValue() == 200){
            return responseEntity.getBody();
        } else {
            throw new RemoteAPIException();
        }
    }

    /**
     * This is a main method of this Service class. It is used to get the page info
     * from the another API endpoint, which can be specified in the conf. file and
     * parse its data into a RawPageData object which is then returned and can be
     * processed as needed.
     *
     * @param targetURL A link to the page data about which is to be received
     * @return An instance of the RawPageData filled with info about the page with "targetURL"
     * @throws RemoteAPIException Thrown as this method just forwards it up from
     * the downloading code
     */
    @Override
    public RawPageData getPageData(String targetURL) throws RemoteAPIException{
        // Object for the data
        RawPageData rawPageData = new RawPageData();

        // Receive data from the remote API, if failed
        // return null value, which is then filtered.
        String data;
        try {
            data = downloadPage(targetURL);
        } catch (RemoteAPIException ex){
            ex.printStackTrace();
            return null;
        }

        // Parse string response body into JSONObject, which
        // provides convenient access to object`s fields.
        JSONObject obj = new JSONObject(data);
        JSONObject result = (JSONObject)obj.getJSONArray("result").get(0);

        // get response`s status
        try {
            rawPageData.setStatus(obj.getString("status"));
        } catch (Exception ex){
            rawPageData.setStatus(null);
            ex.printStackTrace();
        }

        // get page`s body text
        try {
            rawPageData.setBodyText(result.get("body").toString());
        } catch (Exception ex){
            rawPageData.setBodyText(null);
            ex.printStackTrace();
        }

        // get link to the title image
        try {
            if (result.has("titleImage")){
                rawPageData.setTitleImageLink(result.get("titleImage").toString());
            }
        } catch (Exception ex){
            rawPageData.setBodyText(null);
            ex.printStackTrace();
        }

        // for each item in the metadata add it to the metadata map in the
        // RawPageData instance
        try {
            if (result.has("metadata")){
                JSONArray metadata = result.getJSONArray("metadata");
                for(int i = 0; i < metadata.length(); i++){
                    try {
                        JSONObject entry = metadata.getJSONObject(i);
                        rawPageData.addMetadata(entry.getString("name"), entry.getString("content"));
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        // get the actual link to the page
        try {
            rawPageData.setUrl(result.getString("website"));
        } catch (Exception ex){
            ex.printStackTrace();
        }

        // get page`s title
        try {
            rawPageData.setTitle(result.getString("title"));
        } catch (Exception ex){
            ex.printStackTrace();
        }

        // get all links in the body part of the page
        try {
            if (result.has("links")){
                JSONArray links = result.getJSONArray("links");
                for(int i = 0; i < links.length(); i++){
                    try {
                        rawPageData.addLink(links.getString(i));
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        // get all hN tags` content
        try {
            if (result.has("headers")){
                JSONArray headers = result.getJSONArray("headers");
                for(int i = 0; i < headers.length(); i++){
                    try {
                        JSONObject entry = headers.getJSONObject(i);
                        rawPageData.addMetadata(entry.getString("header"), entry.getString("text"));
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        // finally, return the complete object
        return rawPageData;
    }
}
