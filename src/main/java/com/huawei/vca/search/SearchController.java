package com.huawei.vca.search;

import java.util.List;

public interface SearchController {

    List<SearchResult> getRelatedParagraphList(String question);

}
