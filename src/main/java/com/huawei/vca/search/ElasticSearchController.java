package com.huawei.vca.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ElasticSearchController implements SearchController{

    @Autowired
    private RequestFactory requestFactory;

    @Value("${elastic.server.ip}")
    private String serverIp;

    @Value("${elastic.server.port}")
    private String serverPost;


    @Override
    public List<SearchResult> getRElatedParagraphList(String question) {

        RestTemplate restTemplateWithBody = requestFactory.getRestTemplate();

        Map<String, String> matchPhrase = new HashMap<>();
        matchPhrase.put("text", question);
        Map<String, Map<String, String>> match = new HashMap<>();
        match.put("match", matchPhrase);

        List<Map> must = new ArrayList<>();
        must.add(match);
        Map<String, List> bool = new HashMap<>();
        bool.put("must", must);
        Map<String, Map> query = new HashMap<>();
        query.put("bool", bool);
        Map<String, Map> body = new HashMap<>();
        body.put("query", query);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI uri = null;
        try {
            uri = new URI("http://"+serverIp+":"+serverPost+"/qa/_search");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ResponseEntity<Map> exchange = restTemplateWithBody.exchange(uri,
                HttpMethod.GET,
                new HttpEntity<>(body, headers),
                Map.class);

        List<SearchResult>paragraphList = new ArrayList<>();
        Map result = exchange.getBody();
        if (result == null || !result.containsKey("hits")) {
            return paragraphList;
        }

        List hits = (List) ((Map) result.get("hits")).get("hits");
        for (Object hit : hits) {
            String text = (String) ((Map) ((Map) hit).get("_source")).get("text");
            Double score = (Double) ((Map) hit).get("_score");
            paragraphList.add(new SearchResult(score, text));
        }

        return paragraphList;
    }
}
