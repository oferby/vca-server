package com.huawei.vca.web;

import com.huawei.vca.model.QuestionAnswerEntity;
import com.huawei.vca.model.controller.QuestionAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("qa")
public class WebQuestionAnswerRepositoryController {

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

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
                    questionAnswer.setText(newQuestionAnswer.getText());
                    return questionAnswer;
                }).orElseThrow(() ->
                new RuntimeException("could not find requested id")
        );

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        questionAnswerRepository.deleteById(id);
    }


}
