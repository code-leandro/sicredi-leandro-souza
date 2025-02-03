package com.leandrosouza.voting.application.port.in.topic;

import com.leandrosouza.voting.domain.model.TopicModel;

public interface PersistTopicUseCase {

    TopicModel create(TopicModel topicModel);
    TopicModel save(TopicModel topicModel);
}
