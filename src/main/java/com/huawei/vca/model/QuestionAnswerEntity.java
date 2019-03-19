package com.huawei.vca.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "qanda")
public class QuestionAnswerEntity {

    @Id
    private String id;

    private String text;
    private List<String> questions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public void addQuestion(String question){
        if (questions == null) {
            questions = new ArrayList<>();
        }

        questions.add(question);
    }

    @Override
    public String toString() {
        return "QuestionAnswerEntity{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", questions=" + questions +
                '}';
    }
}
