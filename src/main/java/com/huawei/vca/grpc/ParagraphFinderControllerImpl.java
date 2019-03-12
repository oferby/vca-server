package com.huawei.vca.grpc;

import com.huawei.vca.question.QuestionRequest;
import com.huawei.vca.question.QuestionResponse;
import com.huawei.vca.question.QuestionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class ParagraphFinderControllerImpl implements ParagraphFinderController {

    @Value("${qa.grpc.server.ip}")
    private String qaServerIp;

    @Value("${qa.grpc.server.port}")
    private int qaServerPort;

    private QuestionServiceGrpc.QuestionServiceBlockingStub serviceBlockingStub;

    @Override
    public String getParagraph(String question, String[] paragraphs) {

        QuestionRequest.Builder builder = QuestionRequest.newBuilder().setQuestion(question);

        for (String paragraph : paragraphs) {
            builder.addParagraphs(paragraph);
        }

        QuestionRequest request = builder.build();

        QuestionResponse response = serviceBlockingStub.getQuestionResponse(request);

        return response.getParagraph();
    }

    @PostConstruct
    private void setup() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(qaServerIp, qaServerPort)
                .usePlaintext()
                .build();

        serviceBlockingStub = QuestionServiceGrpc.newBlockingStub(channel);

    }
}
