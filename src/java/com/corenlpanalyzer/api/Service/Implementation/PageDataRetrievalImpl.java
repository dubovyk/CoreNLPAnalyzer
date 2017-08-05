package com.corenlpanalyzer.api.Service.Implementation;

import com.corenlpanalyzer.api.Domain.RawPageData;
import com.corenlpanalyzer.api.Service.IPageDataRetrievalService;
import com.corenlpanalyzer.api.Utils.RemoteAPIException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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
        String data = downloadPage(targetURL);

        JSONObject obj = new JSONObject(data);
        JSONObject result = (JSONObject)obj.getJSONArray("result").get(0);
        System.out.println(result.get("body").toString());
        RawPageData rawPageData = new RawPageData();

        rawPageData.setStatus(obj.getString("status"));
        rawPageData.setBodyText(result.get("body").toString());

        return rawPageData;
    }
}
