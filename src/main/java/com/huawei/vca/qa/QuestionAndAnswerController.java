package com.huawei.vca.qa;

public interface QuestionAndAnswerController {


    String getAnswer(String question);

    void addQuestion(String id, String question);

    void addQuestionNot(String id, String question);

}
