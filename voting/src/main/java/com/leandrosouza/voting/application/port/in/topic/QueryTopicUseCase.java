package com.leandrosouza.voting.application.port.in.topic;

import com.leandrosouza.voting.domain.model.TopicModel;

import java.util.List;
import java.util.UUID;

public interface QueryTopicUseCase {
    TopicModel findByIdOrThrowsNotFound(UUID topicId);
    List<TopicModel> findAllWithPagination(Integer pageNumber, Integer pageSize);
    List<TopicModel> findAllOpen();
}
