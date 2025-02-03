package com.leandrosouza.voting.application.service;

import com.leandrosouza.voting.application.port.in.topic.PersistTopicUseCase;
import com.leandrosouza.voting.application.port.in.topic.QueryTopicUseCase;
import com.leandrosouza.voting.application.port.out.TopicRepository;
import com.leandrosouza.voting.domain.model.TopicModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TopicPersistenceService implements
        QueryTopicUseCase,
        PersistTopicUseCase {

    @Autowired
    TopicRepository topicRepository;

    @Override
    public TopicModel create(TopicModel topicModel) {
        log.info("[TopicService > create] name: {}, duration: {}",
                topicModel.getName(), topicModel.getDuration());
        topicModel.setDurationDefaultIfNull();
        return topicRepository.save(topicModel);
    }

    @Override
    public TopicModel findByIdOrThrowsNotFound(UUID id) {
        log.info("[TopicService > findById] id: {}", id);
        return topicRepository.findByIdOrThrowsNotFound(id);
    }

    @Override
    public List<TopicModel> findAllWithPagination(Integer pageNumber, Integer pageSize) {
        log.info("[TopicService > findAll] pageNumber: {}, pageSize: {}", pageNumber, pageSize);
        Pageable page = PageRequest.of(pageNumber, pageSize);
        return topicRepository.findAll(page);
    }

    @Override
    public List<TopicModel> findAllOpen() {
        log.info("[TopicService > findAllOpen]");
        return topicRepository.findAllOpen();
    }

    @Override
    public TopicModel save(TopicModel topicModel) {
        log.info("[TopicService > save] name: {}", topicModel.getName());
        return topicRepository.save(topicModel);
    }
}
