package com.leandrosouza.voting.adapters.in.rest.v1;

import com.leandrosouza.voting.adapters.in.dto.request.TopicInDTO;
import com.leandrosouza.voting.adapters.in.dto.response.TopicOutDTO;
import com.leandrosouza.voting.adapters.mapper.TopicMapper;
import com.leandrosouza.voting.application.port.in.topic.OpenTopicUseCase;
import com.leandrosouza.voting.application.port.in.result.QueryResultVotingUseCase;
import com.leandrosouza.voting.application.port.in.topic.PersistTopicUseCase;
import com.leandrosouza.voting.application.port.in.topic.QueryTopicUseCase;
import com.leandrosouza.voting.application.service.ResultService;
import com.leandrosouza.voting.domain.model.TopicModel;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
class TopicController implements TopicControllerProtocol {

    @Autowired
    PersistTopicUseCase persistTopicUseCase;

    @Autowired
    OpenTopicUseCase openTopicUseCase;

    @Autowired
    QueryTopicUseCase queryTopicUseCase;

    @Autowired
    ResultService resultService;

    @Override
    public ResponseEntity<TopicOutDTO> create(@RequestBody @Valid TopicInDTO topicInDTO) {
        log.info("[TopicController > create] topicInDTO: {}", topicInDTO);
        TopicModel topicModel = TopicMapper.toTopicModel(topicInDTO);
        topicModel = persistTopicUseCase.create(topicModel);
        TopicOutDTO topicOutDTO = TopicMapper.toTopicOutDTO(topicModel);
        return ResponseEntity.created(URI.create("/" + topicModel.getId())).body(topicOutDTO);
    }

    @Override
    public ResponseEntity<Void> send(@PathVariable UUID topicId) {
        resultService.sendResult(topicId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> openTopic(@PathVariable UUID id) {
        openTopicUseCase.open(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<TopicOutDTO>> findAll(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {

        List<TopicOutDTO> list = queryTopicUseCase
                .findAllWithPagination(pageNumber, pageSize)
                .stream().map(TopicMapper::toTopicOutDTO).toList();
        return ResponseEntity.ok(list);
    }
}

