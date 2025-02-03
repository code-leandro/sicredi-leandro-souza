package com.leandrosouza.voting.application.service;

import com.leandrosouza.voting.application.port.in.result.CommunicateResultUseCase;
import com.leandrosouza.voting.application.port.in.topic.*;
import com.leandrosouza.voting.domain.exception.TopicCannotBeOpenedException;
import com.leandrosouza.voting.domain.model.TopicModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TopicServiceTest {

    @InjectMocks
    private TopicService topicService;

    @Mock
    private QueryTopicUseCase queryTopicUseCase;

    @Mock
    private CommunicateResultUseCase communicateResultUseCase;

    @Mock
    private PersistTopicUseCase persistTopicUseCase;

    @Mock
    private ScheduledExecutorService scheduler;

    @Mock
    private Logger log;


    @Test
    void closeTopic_shouldUpdateTopicAndSendResult() {
        TopicModel topicModel = new TopicModel();
        topicModel.setId(UUID.randomUUID());

        topicService.closeTopic(topicModel);

        assertTrue(!topicModel.isOpen());
        assertNotNull(topicModel.getEffectiveEndTime());
        verify(persistTopicUseCase).save(topicModel);
        verify(communicateResultUseCase).sendResult(topicModel.getId());
    }

    @Test
    void open_shouldOpenTopicAndScheduleClosing() {
        UUID id = UUID.randomUUID();
        TopicModel topicModel = new TopicModel();
        topicModel.setDuration(1);
        topicModel.setOpen(false);

        when(queryTopicUseCase.findByIdOrThrowsNotFound(id)).thenReturn(topicModel);

        topicService.open(id);

        assertTrue(topicModel.isOpen());
        assertNotNull(topicModel.getStartTime());
        assertNotNull(topicModel.getPredictedEndTime());
        verify(persistTopicUseCase).save(topicModel);
    }

    @Test
    void open_shouldThrowExceptionIfCannotBeOpened() {
        UUID id = UUID.randomUUID();
        TopicModel topicModel = new TopicModel();
        topicModel.setStartTime(LocalDateTime.now());
        topicModel.setId(id);

        when(queryTopicUseCase.findByIdOrThrowsNotFound(id)).thenReturn(topicModel);

        assertThrows(TopicCannotBeOpenedException.class, () -> topicService.open(id));
        verify(persistTopicUseCase, never()).save(any());
    }

    @Test
    void verifyAllTopicsToClose_shouldCloseOpenTopics() {
        TopicModel topic1 = new TopicModel();
        topic1.setOpen(true);
        topic1.setDuration(1);
        topic1.setPredictedEndTime(LocalDateTime.now().minusMinutes(2)); // Past predicted end time

        TopicModel topic2 = new TopicModel();
        topic2.setOpen(true);
        topic2.setDuration(10);
        topic2.setPredictedEndTime(LocalDateTime.now().plusMinutes(1)); // Future predicted end time

        List<TopicModel> openTopics = List.of(topic1, topic2);

        when(queryTopicUseCase.findAllOpen()).thenReturn(openTopics);

        topicService.verifyAllTopicsToClose();

        assertFalse(topic1.isOpen());
        assertTrue(topic2.isOpen());
        verify(persistTopicUseCase).save(topic1);
        verify(communicateResultUseCase).sendResult(topic1.getId());

    }
}