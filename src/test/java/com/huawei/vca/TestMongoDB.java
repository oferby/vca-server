package com.huawei.vca;

import com.huawei.vca.model.QuestionAnswerEntity;
import com.huawei.vca.model.controller.QuestionAnswerRepository;
import com.huawei.vca.web.WebQuestionAnswerRepositoryController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMongoDB {

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    private WebQuestionAnswerRepositoryController webQuestionAnswerRepositoryController;

    @Test
    public void testGetEntities(){

        List<QuestionAnswerEntity> all = questionAnswerRepository.findAll();

        assert !all.isEmpty();

        System.out.println(all.get(0));


    }

    @Test
    public void testWebController(){

        QuestionAnswerEntity entity = new QuestionAnswerEntity();
        String answer = "this is the answer";
        entity.setText(answer);
        entity.addQuestion("this is the first question");
        entity.addQuestion("this is the second question");

        QuestionAnswerEntity saved = webQuestionAnswerRepositoryController.save(entity);

        assert saved != null && saved.getId() != null && saved.getText().equals(answer);

        saved.setText("another answer");
        saved.addQuestion("another question");

        QuestionAnswerEntity update = webQuestionAnswerRepositoryController.update(saved, saved.getId());

        assert update != null && !update.getText().equals(answer) && update.getQuestions().size() == 3;

        String idToDelete = update.getId();
        webQuestionAnswerRepositoryController.delete(idToDelete);

        Optional<QuestionAnswerEntity> shouldNotBeFound = webQuestionAnswerRepositoryController.getById(idToDelete);
        assert !shouldNotBeFound.isPresent();

    }

}
