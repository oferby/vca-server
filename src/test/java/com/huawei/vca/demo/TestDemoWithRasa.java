package com.huawei.vca.demo;

import com.huawei.vca.question.QuestionServiceGrpc;
import com.huawei.vca.web.ChatbotDemoRasaController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemoWithRasa {


    @Autowired
    private ChatbotDemoRasaController chatbotDemoRasaController;

    @Test
    public void testChatService(){

        String response = chatbotDemoRasaController.getResponse("hello");

        System.out.println(response);


    }

}
