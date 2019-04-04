package com.huawei.vca.demo;

import com.huawei.vca.message.Dialogue;
import com.huawei.vca.web.demo.ChatbotDemoRasaController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemoWithRasa {


    @Autowired
    private ChatbotDemoRasaController chatbotDemoRasaController;

    @Test
    public void testChatService() {

        Dialogue dialogue = new Dialogue();
        dialogue.setText("hello");
        Dialogue response = chatbotDemoRasaController.getResponse(dialogue);

        System.out.println(response);

        dialogue.setText("what is your name?");
        response = chatbotDemoRasaController.getResponse(dialogue);
        System.out.println(response);

        dialogue.setText("are you human?");
        response = chatbotDemoRasaController.getResponse(dialogue);
        System.out.println(response);

        dialogue.setText("what can you do?");
        response = chatbotDemoRasaController.getResponse(dialogue);
        System.out.println(response);
    }

}
