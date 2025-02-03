package com.leandrosouza.voting.application.service;

import com.leandrosouza.voting.adapters.in.dto.publisher.ResultDTO;
import com.leandrosouza.voting.adapters.mapper.ResultMapper;
import com.leandrosouza.voting.application.port.out.Publisher;
import com.leandrosouza.voting.application.port.in.result.QueryResultVotingUseCase;
import com.leandrosouza.voting.domain.model.ResultVotesModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResultServiceTest {

    @InjectMocks
    private ResultService resultService;

    @Mock
    private Publisher publisher;

    @Mock
    private QueryResultVotingUseCase queryResultVotingUseCase;

    @Test
    void sendResult_shouldCallPublisherWithMappedResult() {
        UUID topicId = UUID.randomUUID();
        ResultVotesModel resultVotesModel = new ResultVotesModel();
        resultVotesModel.setTopicId(topicId);
        ResultDTO resultDTO = ResultMapper.toResultDTO(resultVotesModel);

        when(queryResultVotingUseCase.result(topicId)).thenReturn(resultVotesModel);

        resultService.sendResult(topicId);

        verify(queryResultVotingUseCase).result(topicId);
        verify(publisher).sendResult(resultDTO);
    }
}