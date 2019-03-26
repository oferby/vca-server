package com.huawei.vca.grpc;

public class ParagraphResult {

    private String paragraph;
    private float probability;

    public ParagraphResult(String paragraph, float probability) {
        this.paragraph = paragraph;
        this.probability = probability;
    }

    public String getParagraph() {
        return paragraph;
    }

    public float getProbability() {
        return probability;
    }

    @Override
    public String toString() {
        return "ParagraphResult{" +
                "paragraph='" + paragraph + '\'' +
                ", probability=" + probability +
                '}';
    }
}
