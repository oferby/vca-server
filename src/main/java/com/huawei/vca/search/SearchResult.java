package com.huawei.vca.search;

public class SearchResult {
    private Double score;
    private String text;

    public SearchResult(Double score, String text) {
        this.score = score;
        this.text = text;
    }

    public Double getScore() {
        return score;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "score=" + score +
                ", text='" + text + '\'' +
                '}';
    }
}
