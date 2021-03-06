package com.huawei.vca.grpc;

import com.huawei.vca.question.QuestionRequest;
import com.huawei.vca.question.QuestionResponse;
import com.huawei.vca.question.QuestionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class ParagraphFinderControllerImpl implements ParagraphFinderController {

    private static final Logger logger = LoggerFactory.getLogger(ParagraphFinderControllerImpl.class);

    @Value("${qa.grpc.server.ip}")
    private String qaServerIp;

    @Value("${qa.grpc.server.port}")
    private int qaServerPort;

    private QuestionServiceGrpc.QuestionServiceBlockingStub serviceBlockingStub;

    @Override
    public ParagraphResult getParagraph(String question, String[] paragraphs) {

        QuestionRequest.Builder builder = QuestionRequest.newBuilder().setQuestion(question);

        for (String paragraph : paragraphs) {
            builder.addParagraphs(paragraph);
        }

        QuestionRequest request = builder.build();

        QuestionResponse response = serviceBlockingStub.getQuestionResponse(request);

        String paragraph = paragraphs[response.getArgmax()];

        return new ParagraphResult(paragraph, response.getProbability(response.getArgmax()));

    }

    @PostConstruct
    private void setup() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(qaServerIp, qaServerPort)
                .usePlaintext()
                .build();

        serviceBlockingStub = QuestionServiceGrpc.newBlockingStub(channel);

    }
}
