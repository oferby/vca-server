package com.huawei.vca.web.demo;

import com.huawei.vca.chat.ChatRequest;
import com.huawei.vca.chat.ChatResponse;
import com.huawei.vca.chat.ChatServiceGrpc;
import com.huawei.vca.message.Dialogue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("api/rasa")
public class ChatbotDemoRasaController {

    @Value("${chat.grpc.server.ip}")
    private String chatServerIp;

    @Value("${chat.grpc.server.port}")
    private int chatServerPort;

    private ChatServiceGrpc.ChatServiceBlockingStub serviceBlockingStub;

    @PostConstruct
    private void setup() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(chatServerIp, chatServerPort)
                .usePlaintext()
                .build();
        serviceBlockingStub = ChatServiceGrpc.newBlockingStub(channel);
    }

    @PostMapping
    public Dialogue getResponse(@RequestBody Dialogue dialogue) {

        ChatRequest chatRequest = ChatRequest.newBuilder().setText(dialogue.getText()).build();

        ChatResponse chatResponse = serviceBlockingStub.getChatResponse(chatRequest);

        dialogue.setText(chatResponse.getText());
        return dialogue;

    }


}
