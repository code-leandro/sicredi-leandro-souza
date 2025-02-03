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

import static org.assertj.core.api.Assertions.assertThat;
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
        VoteInDTO voteInDTO = new VoteInDTO(
                true,
                UUID.randomUUID(),
                "123");
        VoteModel voteModelIn = VoteMapper.toVoteModel(voteInDTO);


        UUID voteId = UUID.randomUUID();
        VoteModel voteModel = new VoteModel(voteId, voteInDTO.option(), voteInDTO.topicId(), voteInDTO.cpf());
        VoteOutDTO voteOutDTO = VoteMapper.toVoteOutDTO(voteModel);

        when(voteUseCase.vote(voteModelIn)).thenReturn(voteModel);

        ResponseEntity<VoteOutDTO> response = voteController.vote(voteInDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(voteOutDTO);
        assertThat(response.getBody().id()).isEqualTo(voteId);
    }
}