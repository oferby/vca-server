package com.huawei.vca.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "qanda")
public class QuestionAnswerEntity {

    @Id
    private String id;

    private String paragraph;
    private String url;
    private List<String> questions;
    private List<String> questions_not;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<String> getQuestions_not() {
        return questions_not;
    }

    public void setQuestions_not(List<String> questions_not) {
        this.questions_not = questions_not;
    }

    public void addQuestion(String question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }

        questions.add(question);
    }

    public void addQuestionNot(String questionNot) {

        if (questionNot == null)
            questions_not = new ArrayList<>();

        questions_not.add(questionNot);

    }

    @Override
    public String toString() {
        return "QuestionAnswerEntity{" +
                "id='" + id + '\'' +
                ", paragraph='" + paragraph + '\'' +
                ", url='" + url + '\'' +
                ", questions=" + questions +
                ", questions_not=" + questions_not +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionAnswerEntity entity = (QuestionAnswerEntity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(paragraph, entity.paragraph) &&
                Objects.equals(url, entity.url) &&
                Objects.equals(questions, entity.questions) &&
                Objects.equals(questions_not, entity.questions_not);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, paragraph, url, questions, questions_not);
    }
}
