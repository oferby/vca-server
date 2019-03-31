package com.huawei.vca.web;

import com.huawei.vca.model.QuestionAnswerEntity;
import com.huawei.vca.model.QuestionHistory;
import com.huawei.vca.model.controller.QuestionAnswerRepository;
import com.huawei.vca.model.controller.QuestionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("data/qa")
public class WebQuestionAnswerRepositoryController {

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    private QuestionHistoryRepository questionHistoryRepository;

    @GetMapping(produces = "application/json")
    public List<QuestionAnswerEntity> getAll() {
        return questionAnswerRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<QuestionAnswerEntity> getById(@PathVariable String id) {
        return questionAnswerRepository.findById(id);
    }

    @PostMapping
    public QuestionAnswerEntity save(@RequestBody QuestionAnswerEntity entity) {
        return questionAnswerRepository.save(entity);
    }

    @PutMapping("{id}")
    public QuestionAnswerEntity update(@RequestBody QuestionAnswerEntity newQuestionAnswer, @PathVariable String id) {

        return questionAnswerRepository.findById(id).map(
                questionAnswer -> {
                    questionAnswer.setQuestions(newQuestionAnswer.getQuestions());
                    questionAnswer.setParagraph(newQuestionAnswer.getParagraph());
                    return questionAnswer;
                }).orElseThrow(() ->
                new RuntimeException("could not find requested id")
        );

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        questionAnswerRepository.deleteById(id);
    }


    @GetMapping("/history")
    public List<QuestionHistory> getQuestionHistories() {
        return questionHistoryRepository.findAll();

    }

    @DeleteMapping("/history")
    public void deleteAll(){
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
