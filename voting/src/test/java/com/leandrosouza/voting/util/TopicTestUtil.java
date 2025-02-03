package com.leandrosouza.voting.util;

import com.leandrosouza.voting.domain.model.TopicModel;

import java.util.UUID;

public class TopicTestUtil {

    public static final UUID ID_1 = UUID.randomUUID();
    public static final String NAME_1 = "topic 1";
    public static final String CPF_1 = "12312312312";

    public static TopicModel instanceTopicModel() {
        return TopicModel
                .builder()
                .id(ID_1)
                .name(NAME_1)
                .build();
    }
}
