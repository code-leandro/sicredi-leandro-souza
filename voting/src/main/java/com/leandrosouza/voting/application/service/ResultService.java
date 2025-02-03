package com.leandrosouza.voting.application.service;

import com.leandrosouza.voting.adapters.in.dto.publisher.ResultDTO;
import com.leandrosouza.voting.adapters.mapper.ResultMapper;
import com.leandrosouza.voting.application.port.out.Publisher;
import com.leandrosouza.voting.application.port.in.result.QueryResultVotingUseCase;
import com.leandrosouza.voting.domain.model.ResultVotesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leandrosouza.voting.application.port.in.result.CommunicateResultUseCase;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Service
public class ResultService implements CommunicateResultUseCase {

    @Autowired
    private Publisher publisher;

    @Autowired
    private QueryResultVotingUseCase queryResultVotingUseCase;

    @Override
    public void sendResult(UUID topicId) {
        log.info("[ResultService > sendResult] topicId: {}", topicId);
        ResultVotesModel resultVotesModel = queryResultVotingUseCase.result(topicId);
        ResultDTO resultDTO = ResultMapper.toResultDTO(resultVotesModel);
        publisher.sendResult(resultDTO);
    }
}
