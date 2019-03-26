package com.huawei.vca;

import com.huawei.vca.qa.QuestionAndAnswerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestQuestionsAnswerController {

    @Autowired
    private QuestionAndAnswerController questionAndAnswerController;

    @Test
    public void testGetAnswer() {

//        String question = "can I ping my ECS?";
//        String question = "what is ecs?";
        String question = "what is ec2?";
//        String question = "can I upgrade my vm os?";
//        String question = "i need information on virtual server";

        String answer = questionAndAnswerController.getAnswer(question);

        assert answer != null;

        System.out.println(answer);


    }


}
