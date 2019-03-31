package com.huawei.vca.search;

public class MultiMatchSearchQuery {

    private String query;
    private String type = "best_fields";
    private String[] fields = new String[] {"paragraph","questions"};

    public MultiMatchSearchQuery() {

    }

    public MultiMatchSearchQuery(String query, String type, String[] fields) {
        this.query = query;
        this.type = type;
        this.fields = fields;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }
}
