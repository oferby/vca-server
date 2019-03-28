package com.huawei.vca.model.controller;

import com.huawei.vca.model.QuestionHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionHistoryRepository extends MongoRepository<QuestionHistory, String> {



}
