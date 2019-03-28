package com.huawei.vca.model.controller;

import com.huawei.vca.model.QuestionAnswerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionAnswerRepository extends MongoRepository<QuestionAnswerEntity, String> {

    QuestionAnswerEntity findByParagraph(String paragraph);

}
