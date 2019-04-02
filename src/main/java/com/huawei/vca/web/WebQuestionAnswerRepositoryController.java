package com.huawei.vca.web;

import com.huawei.vca.model.QuestionAnswerEntity;
import com.huawei.vca.model.QuestionHistory;
import com.huawei.vca.model.controller.QuestionAnswerRepository;
import com.huawei.vca.model.controller.QuestionHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("data/qa")
public class WebQuestionAnswerRepositoryController {

    private static final Logger logger = LoggerFactory.getLogger(WebQuestionAnswerRepositoryController.class);

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    private QuestionHistoryRepository questionHistoryRepository;

    @GetMapping(produces = "application/json")
    public List<QuestionAnswerEntity> getAll() {

        logger.debug("got get all questions request.");

        return questionAnswerRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<QuestionAnswerEntity> getById(@PathVariable String id) {

        logger.debug("got get by id questions request.");
        return questionAnswerRepository.findById(id);
    }

    @PostMapping
    public QuestionAnswerEntity save(@RequestBody QuestionAnswerEntity entity) {
        logger.debug("got save question request.");
        return questionAnswerRepository.save(entity);
    }

    @PutMapping("{id}")
    public List<QuestionAnswerEntity> update(@RequestBody QuestionAnswerEntity newQuestionAnswer, @PathVariable String id) {
        logger.debug("got update questions request.");
        Optional<QuestionAnswerEntity> answerEntity = questionAnswerRepository.findById(id);
        QuestionAnswerEntity entity = answerEntity.map(
                questionAnswer -> {
                    questionAnswer.setQuestions(newQuestionAnswer.getQuestions());
                    questionAnswer.setParagraph(newQuestionAnswer.getParagraph());

                    return questionAnswer;
                }).orElseThrow(() ->
                new RuntimeException("could not find requested id")
        );
        questionAnswerRepository.save(entity);

        return getAll();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {

        logger.debug("got delete questions request.");
        questionAnswerRepository.deleteById(id);
    }


    @GetMapping("/history")
    public List<QuestionHistory> getQuestionHistories() {
        logger.debug("got get questions history request.");
        return questionHistoryRepository.findAll();

    }

    @DeleteMapping("/history")
    public void deleteAll(){

        logger.debug("got get delete questions history request.");
        questionHistoryRepository.deleteAll();
    }


    @PutMapping("/history/{id}/{isCorrect}")
    public void updateParagraph(@PathVariable String id, @PathVariable Boolean isCorrect) {

        Optional<QuestionHistory> history = questionHistoryRepository.findById(id);
        if (!history.isPresent()) {
            throw new RuntimeException("did not find history");
        }

        QuestionAnswerEntity questionAnswerEntity = questionAnswerRepository.findByParagraph(history.get().getParagraph());
        if (questionAnswerEntity==null)
            throw new RuntimeException("could not find question");

        String question = history.get().getQuestion();

        if (isCorrect) {

            for (String q : questionAnswerEntity.getQuestions()) {
                if (q.equals(question))
                    return;
            }

            questionAnswerEntity.addQuestion(question);

        } else {

            for (String q : questionAnswerEntity.getQuestions_not()) {
                if (q.equals(question))
                    return;
            }

            questionAnswerEntity.addQuestionNot(question);

        }

        questionHistoryRepository.delete(history.get());
    }


}
