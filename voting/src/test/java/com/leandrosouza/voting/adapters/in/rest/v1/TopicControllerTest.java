package com.leandrosouza.voting.adapters.in.rest.v1;

import com.leandrosouza.voting.adapters.in.dto.request.TopicInDTO;
import com.leandrosouza.voting.adapters.in.dto.response.TopicOutDTO;
import com.leandrosouza.voting.adapters.in.rest.v1.TopicController;
import com.leandrosouza.voting.adapters.mapper.TopicMapper;
import com.leandrosouza.voting.application.port.in.topic.OpenTopicUseCase;
import com.leandrosouza.voting.application.port.in.topic.PersistTopicUseCase;
import com.leandrosouza.voting.application.port.in.topic.QueryTopicUseCase;
import com.leandrosouza.voting.application.service.ResultService;
import com.leandrosouza.voting.domain.model.TopicModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TopicControllerTest {

    @InjectMocks
    private TopicController topicController;

    @Mock
    private PersistTopicUseCase persistTopicUseCase;

    @Mock
    private OpenTopicUseCase openTopicUseCase;

    @Mock
    private QueryTopicUseCase queryTopicUseCase;

    @Mock
    private ResultService resultService;

    @Mock
    private Logger log;

    @Test
    void create_shouldReturnCreatedTopic() {
        TopicInDTO topicInDTO = new TopicInDTO();
        topicInDTO.setName("Test Topic");
        topicInDTO.setDuration(10);

        TopicModel topicModel = TopicMapper.toTopicModel(topicInDTO);
        TopicOutDTO topicOutDTO = TopicMapper.toTopicOutDTO(topicModel);

        when(persistTopicUseCase.create(topicModel)).thenReturn(topicModel);

        ResponseEntity<TopicOutDTO> response = topicController.create(topicInDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(topicOutDTO, response.getBody());
        verify(persistTopicUseCase).create(topicModel);
    }

    @Test
    void send_shouldCallResultService() {
        UUID topicId = UUID.randomUUID();

        ResponseEntity<Void> response = topicController.send(topicId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(resultService).sendResult(topicId);
    }

    @Test
    void openTopic_shouldCallOpenTopicUseCase() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> response = topicController.openTopic(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(openTopicUseCase).open(id);
    }

    @Test
    void findAll_shouldReturnListOfTopics() {
        int pageNumber = 0;
        int pageSize = 10;
        UUID topicId = UUID.randomUUID();
        TopicModel topicModel = TopicModel.builder().id(topicId).build();
        List<TopicModel> topicModels = List.of(topicModel);
        TopicOutDTO topicOutDTO = TopicMapper.toTopicOutDTO(topicModel);

        when(queryTopicUseCase.findAllWithPagination(pageNumber, pageSize)).thenReturn(topicModels);

        ResponseEntity<List<TopicOutDTO>> response = topicController.findAll(pageNumber, pageSize);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(topicOutDTO), response.getBody());
        assertEquals(topicId, response.getBody().get(0).getId());
        verify(queryTopicUseCase).findAllWithPagination(pageNumber, pageSize);
    }
}