package com.huawei.vca.qa;

import com.huawei.vca.grpc.ParagraphFinderController;
import com.huawei.vca.grpc.ParagraphResult;
import com.huawei.vca.model.QuestionAnswerEntity;
import com.huawei.vca.model.QuestionHistory;
import com.huawei.vca.model.controller.QuestionAnswerRepository;
import com.huawei.vca.model.controller.QuestionHistoryRepository;
import com.huawei.vca.search.SearchController;
import com.huawei.vca.search.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Controller
public class SimpleQuestionAndAnswerController implements QuestionAndAnswerController {

    @Autowired
    private SearchController searchController;

    @Autowired
    private
    QuestionHistoryRepository questionHistoryRepository;

    @Autowired
    private
    QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    private ParagraphFinderController paragraphFinderController;

    @Autowired
    private ExecutorService executorService;

    private static final Logger logger = LoggerFactory.getLogger(SimpleQuestionAndAnswerController.class);

    private static final int numOfParagraphs = 10;

    private static final String noAnswerResponse = "Sorry, I have no answer to this question...";

    @Override
    public String getAnswer(String question) {

        List<SearchResult> relatedParagraphList = searchController.getRelatedParagraphList(question);
        int size = relatedParagraphList.size();

        if (size == 0) {
            logToHistory(question, noAnswerResponse, 0);
            return noAnswerResponse;
        }

        if (size > 10)
            throw new NotImplementedException();

        String[] paragraps = new String[numOfParagraphs];

        for (int i = 0; i < size; i++) {
            paragraps[i] = relatedParagraphList.get(i).getText();
        }

        if (size < numOfParagraphs) {

            int addParagraphs = 10 - size;
            for (int i = 0; i < addParagraphs; i++) {
                paragraps[size + i] = "EMPTY EMPTY EMPTY EMPTY EMPTY";
            }

        }

        ParagraphResult paragraphResult = paragraphFinderController.getParagraph(question, paragraps);
        logger.debug("**** " + paragraphResult + " *****");

        if (paragraphResult.getProbability() < 0.7) {
            logToHistory(question, noAnswerResponse, paragraphResult.getProbability());
            return noAnswerResponse;
        }

        logToHistory(question, paragraphResult.getParagraph(), paragraphResult.getProbability());

        return paragraphResult.getParagraph();
    }


    private void logToHistory(String question, String paragraph, float score) {
        executorService.execute(() -> {

            QuestionHistory questionHistory = new QuestionHistory();

            if (!paragraph.equals(noAnswerResponse)) {
                QuestionAnswerEntity answerEntity = questionAnswerRepository.findByParagraph(paragraph);

                if (answerEntity == null)
                    throw new RuntimeException("could not find paragraph");

                questionHistory.setParagraph_id(answerEntity.getId());
            }

            questionHistory.setParagraph(paragraph);
            questionHistory.setScore(score);
            questionHistory.setQuestion(question);

            questionHistoryRepository.save(questionHistory);
        });
    }

}
