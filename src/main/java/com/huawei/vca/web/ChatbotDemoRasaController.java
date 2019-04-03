package com.huawei.vca.web;

import com.huawei.vca.chat.ChatRequest;
import com.huawei.vca.chat.ChatResponse;
import com.huawei.vca.chat.ChatServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public String getResponse(String request) {

        ChatRequest chatRequest = ChatRequest.newBuilder().setText(request).build();

        ChatResponse chatResponse = serviceBlockingStub.getChatResponse(chatRequest);

        return chatResponse.getText();

    }


}
