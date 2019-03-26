package com.huawei.vca.grpc;

public interface ParagraphFinderController {

    ParagraphResult getParagraph(String question, String[] paragraphs);

}
