package com.huawei.vca;

import com.huawei.vca.question.QuestionProto;
import com.huawei.vca.question.QuestionRequest;
import com.huawei.vca.question.QuestionResponse;
import com.huawei.vca.question.QuestionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGrpc {

    @Value("${qa.grpc.server.ip}")
    private String qaServerIp;

    @Value("${qa.grpc.server.port}")
    private int qaServerPort;

    @Autowired
    private ExecutorService executorService;

    @Test
    public void testGrpc() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(qaServerIp, qaServerPort)
                .usePlaintext()
                .build();

        for (int i = 0; i < 20; i++) {

            int finalI = i;
            executorService.execute(() -> {

                QuestionServiceGrpc.QuestionServiceBlockingStub serviceBlockingStub = QuestionServiceGrpc.newBlockingStub(channel);

                QuestionRequest request = QuestionRequest.newBuilder()
                        .setQuestion("this is my" + finalI + " question")
                        .addParagraphs("this is the " + finalI + " paragraph")
                        .build();
                QuestionResponse questionResponse =
                        serviceBlockingStub.getQuestionResponse(request);

                assert questionResponse != null;
                System.out.println(finalI + ": " + questionResponse.toString());

            });

        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testMultipleParagraphs() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(qaServerIp, qaServerPort)
                .usePlaintext()
                .build();

        for (int i = 0; i < 4; i++) {

            int finalI = i;
            executorService.execute(() -> {

                QuestionServiceGrpc.QuestionServiceBlockingStub serviceBlockingStub = QuestionServiceGrpc.newBlockingStub(channel);

                QuestionRequest.Builder builder = QuestionRequest.newBuilder()
                        .setQuestion("this is my" + finalI + " question");

                for (int j = 0; j < 4; j++) {
                    builder.addParagraphs("this is the " + finalI + " paragraph_" + j);
                }

                QuestionRequest request = builder.build();
                QuestionResponse questionResponse =
                        serviceBlockingStub.getQuestionResponse(request);

                assert questionResponse != null;
                System.out.println(finalI + ":\n" + questionResponse.toString());

            });

        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testMultipleParagraphsReal() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(qaServerIp, qaServerPort)
                .usePlaintext()
                .build();

        QuestionServiceGrpc.QuestionServiceBlockingStub serviceBlockingStub = QuestionServiceGrpc.newBlockingStub(channel);
        QuestionRequest.Builder builder = QuestionRequest.newBuilder()
                .setQuestion("is this a test?");

        builder.addParagraphs("");
        builder.addParagraphs("this is a test paragraph.");
        builder.addParagraphs("the cancellation meant the sacking of 70 staff and millions of pounds");
        builder.addParagraphs("this is unrelated paragraph");

        QuestionRequest request = builder.build();
        QuestionResponse questionResponse =
                serviceBlockingStub.getQuestionResponse(request);

        assert questionResponse != null;
        System.out.println(questionResponse.toString());


    }
}
