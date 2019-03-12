package com.huawei.vca;

import com.huawei.vca.question.QuestionProto;
import com.huawei.vca.question.QuestionRequest;
import com.huawei.vca.question.QuestionResponse;
import com.huawei.vca.question.QuestionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGrpc {

    @Value("${qa.grpc.server.ip}")
    private String qaServerIp;

    @Value("${qa.grpc.server.port}")
    private int qaServerPort;

    @Test
    public void testGrpc() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(qaServerIp, qaServerPort)
                .usePlaintext()
                .build();

        QuestionServiceGrpc.QuestionServiceBlockingStub serviceBlockingStub = QuestionServiceGrpc.newBlockingStub(channel);

        QuestionRequest request = QuestionRequest.newBuilder()
                .setQuestion("this is my question")
                .addParagraphs("this is a paragraph")
                .build();
        QuestionResponse questionResponse =
                serviceBlockingStub.getQuestionResponse(request);

        assert questionResponse != null;
        System.out.println(questionResponse.toString());
    }

}
