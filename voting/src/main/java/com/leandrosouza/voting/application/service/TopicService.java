package com.leandrosouza.voting.application.service;

import com.leandrosouza.voting.application.port.in.result.CommunicateResultUseCase;
import com.leandrosouza.voting.application.port.in.topic.*;
import com.leandrosouza.voting.domain.model.TopicModel;
import com.leandrosouza.voting.domain.exception.TopicCannotBeOpenedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TopicService implements
        OpenTopicUseCase,
        CloseTopicUseCase,
        InspectAllTopicAndCloseUseCase {

    public static final String TOPIC_CANNOT_BE_OPENED = "Topic cannot be opened";

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Autowired
    private QueryTopicUseCase queryTopicUseCase;

    @Autowired
    private CommunicateResultUseCase communicateResultUseCase;

    @Autowired
    private PersistTopicUseCase persistTopicUseCase;

    @Override
    public void closeTopic(TopicModel topicModel) {
        log.info("[TopicService > closeTopic] id: {}", topicModel.getId());
        topicModel.setEffectiveEndTime(LocalDateTime.now());
        topicModel.setOpen(false);
        persistTopicUseCase.save(topicModel);
        communicateResultUseCase.sendResult(topicModel.getId());
        log.info("[TopicService > closeTopic] {} close!", topicModel.getId());
    }

    @Override
    public void open(UUID id) {
        log.info("[TopicService > openTopic] id: {}", id);
        TopicModel topicModel = queryTopicUseCase.findByIdOrThrowsNotFound(id);
        if (! topicModel.hasConditionsToOpen())
            throw new TopicCannotBeOpenedException(TOPIC_CANNOT_BE_OPENED);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(topicModel.getDuration());
        topicModel.setOpen(true);
        topicModel.setStartTime(start);
        topicModel.setPredictedEndTime(end);
        persistTopicUseCase.save(topicModel);
        scheduler.schedule(this::verifyAllTopicsToClose, topicModel.getDuration(), TimeUnit.MINUTES);
    }

    @Override
    public void verifyAllTopicsToClose() {
        log.info("[TopicService > verifyAllTopicsToClose]");
        queryTopicUseCase.findAllOpen().forEach(t -> {
            if (t.hasConditionToClose()) {
                closeTopic(t);
            }
        });
    }
}
