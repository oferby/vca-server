package com.huawei.vca.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ElasticSearchController implements SearchController{

    @Autowired
    private RequestFactory requestFactory;


}
