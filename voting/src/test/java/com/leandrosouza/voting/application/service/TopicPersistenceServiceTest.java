package com.leandrosouza.voting.application.service;

import com.leandrosouza.voting.application.port.in.topic.PersistTopicUseCase;
import com.leandrosouza.voting.application.port.in.topic.QueryTopicUseCase;
import com.leandrosouza.voting.application.service.TopicPersistenceService;
import com.leandrosouza.voting.domain.model.TopicModel;
import com.leandrosouza.voting.application.port.out.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TopicPersistenceServiceTest {

    @InjectMocks
    private TopicPersistenceService topicService;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private Logger log;

    @Test
    void create_shouldSaveTopicAndSetDefaultDuration() {
        TopicModel topicModel = new TopicModel();
        topicModel.setName("Test Topic");
        topicModel.setDuration(null); // Duration is null initially

        TopicModel savedTopic = new TopicModel();
        savedTopic.setId(UUID.randomUUID());
        savedTopic.setName("Test Topic");
        savedTopic.setDuration(1); // Default duration

        when(topicRepository.save(any(TopicModel.class))).thenReturn(savedTopic);

        TopicModel createdTopic = topicService.create(topicModel);

        assertNotNull(createdTopic.getId());
        assertEquals(1, createdTopic.getDuration()); // Check if default is set
        verify(topicRepository).save(topicModel);
    }

    @Test
    void findByIdOrThrowsNotFound_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        TopicModel topicModel = new TopicModel();

        when(topicRepository.findByIdOrThrowsNotFound(id)).thenReturn(topicModel);

        TopicModel foundTopic = topicService.findByIdOrThrowsNotFound(id);

        assertEquals(topicModel, foundTopic);
        verify(topicRepository).findByIdOrThrowsNotFound(id);
    }

    @Test
    void findAllWithPagination_shouldCallRepositoryWithPageable() {
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<TopicModel> topicList = List.of(new TopicModel(), new TopicModel());

        when(topicRepository.findAll(pageable)).thenReturn(topicList);

        List<TopicModel> foundTopics = topicService.findAllWithPagination(pageNumber, pageSize);

        assertEquals(topicList, foundTopics);
        verify(topicRepository).findAll(pageable);
    }

    @Test
    void findAllOpen_shouldCallRepository() {
        List<TopicModel> topicList = List.of(new TopicModel(), new TopicModel());

        when(topicRepository.findAllOpen()).thenReturn(topicList);

        List<TopicModel> foundTopics = topicService.findAllOpen();

        assertEquals(topicList, foundTopics);
        verify(topicRepository).findAllOpen();
    }

    @Test
    void save_shouldCallRepository() {
        TopicModel topicModel = new TopicModel();

        topicService.save(topicModel);

        verify(topicRepository).save(topicModel);
    }
}