package com.leandrosouza.voting.adapters.in.rest.v1;

import com.leandrosouza.voting.adapters.in.dto.request.VoteInDTO;
import com.leandrosouza.voting.adapters.in.dto.response.VoteOutDTO;
import com.leandrosouza.voting.adapters.in.rest.v1.VoteController;
import com.leandrosouza.voting.adapters.mapper.VoteMapper;
import com.leandrosouza.voting.application.port.in.vote.VoteUseCase;
import com.leandrosouza.voting.domain.model.VoteModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoteControllerTest {

    @InjectMocks
    private VoteController voteController;

    @Mock
    private VoteUseCase voteUseCase;

    @Test
    void vote_shouldReturnCreatedVote() throws Exception {
        VoteInDTO voteInDTO = new VoteInDTO();
        voteInDTO.setCpf("123");
        voteInDTO.setTopicId(UUID.randomUUID());
        voteInDTO.setOption(true);

        VoteModel voteModel = new VoteModel(null, voteInDTO.getOption(), voteInDTO.getTopicId(), voteInDTO.getCpf());
        VoteOutDTO voteOutDTO = new VoteOutDTO();
        voteOutDTO.setCpf(voteInDTO.getCpf());
        voteOutDTO.setTopicId(voteInDTO.getTopicId());
        voteOutDTO.setOption(voteInDTO.getOption());

        when(voteUseCase.vote(voteModel)).thenReturn(voteModel);

        ResponseEntity<VoteOutDTO> response = voteController.vote(voteInDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(voteOutDTO, response.getBody());
    }
}