package com.huawei.vca.qa;

import com.huawei.vca.grpc.ParagraphFinderController;
import com.huawei.vca.search.SearchController;
import com.huawei.vca.search.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SimpleQuestionAndAnswerController implements QuestionAndAnswerController{

    @Autowired
    private SearchController searchController;

    @Autowired
    private ParagraphFinderController paragraphFinderController;

    private static final Logger logger = LoggerFactory.getLogger(SimpleQuestionAndAnswerController.class);

    @Override
    public String getAnswer(String question) {

        List<SearchResult> relatedParagraphList = searchController.getRelatedParagraphList(question);

        return relatedParagraphList.get(0).getText();

    }
}
