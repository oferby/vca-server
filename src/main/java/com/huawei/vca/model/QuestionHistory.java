package com.huawei.vca.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "qanda_history")
public class QuestionHistory {

    @Id
    private String id;

    private String paragraph;

    private String paragraph_id;

    private String question;

    private float score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getParagraph_id() {
        return paragraph_id;
    }

    public void setParagraph_id(String paragraph_id) {
        this.paragraph_id = paragraph_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "QuestionHistory{" +
                "id='" + id + '\'' +
                ", paragraph='" + paragraph + '\'' +
                ", paragraph_id='" + paragraph_id + '\'' +
                ", question='" + question + '\'' +
                ", score=" + score +
                '}';
    }
}
