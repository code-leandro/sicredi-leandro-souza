package com.leandrosouza.voting.application.port.in.topic;

import com.leandrosouza.voting.domain.model.TopicModel;

public interface CloseTopicUseCase {
    void closeTopic(TopicModel topicModel);
}
