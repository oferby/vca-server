package com.huawei.vca;

import com.huawei.vca.search.RequestFactory;
import com.huawei.vca.search.SearchController;
import com.huawei.vca.search.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestElesticSearch {

    @Autowired
    private RequestFactory requestFactory;

    @Autowired
    private SearchController searchController;

    @Test
    public void restTemplateWithBody() throws URISyntaxException {

        RestTemplate restTemplateWithBody = requestFactory.getRestTemplate();

        Map<String, String> matchPhrase = new HashMap<>();
        matchPhrase.put("text", "new text");
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
        URI uri = new URI("http://10.100.99.85:9200/qa/_search");
        ResponseEntity<Map> exchange = restTemplateWithBody.exchange(uri,
                HttpMethod.GET,
                new HttpEntity<>(body, headers),
                Map.class);

        System.out.println(exchange.getBody());


    }


    @Test
    public void testSearchController(){

        List<SearchResult> paragraphList = searchController.getRElatedParagraphList("what is Content Moderation");

        assert !paragraphList.isEmpty();

        for (SearchResult searchResult : paragraphList) {
            System.out.println(searchResult.toString());
        }




    }
}
