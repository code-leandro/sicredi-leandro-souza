package com.leandrosouza.voting.application.service;

import com.leandrosouza.voting.application.port.in.associate.QueryAssociateUseCase;
import com.leandrosouza.voting.application.port.in.topic.QueryTopicUseCase;
import com.leandrosouza.voting.application.port.out.VoteRepository;
import com.leandrosouza.voting.domain.exception.EntityAlreadyExist;
import com.leandrosouza.voting.domain.exception.VoteCannotBeCastException;
import com.leandrosouza.voting.domain.model.ResultVotesModel;
import com.leandrosouza.voting.domain.model.TopicModel;
import com.leandrosouza.voting.domain.model.VoteModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VotingServiceTest {

    public static final String CPF = "123";
    @InjectMocks
    private VotingService votingService;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private QueryTopicUseCase queryTopicUseCase;

    @Mock
    private QueryAssociateUseCase queryAssociateUseCase;

    @Mock
    private Logger log;

    @Test
    void result_shouldCalculateAndReturnResultVotes() {
        UUID topicId = UUID.randomUUID();
        TopicModel topicModel = new TopicModel();
        topicModel.setName("Test Topic");
        List<VoteModel> votes = List.of(
                new VoteModel(null, true, topicId, "123"),
                new VoteModel(null, true, topicId, "456"),
                new VoteModel(null, false, topicId, "789")
        );

        when(queryTopicUseCase.findByIdOrThrowsNotFound(topicId)).thenReturn(topicModel);
        when(voteRepository.findByTopicId(topicId)).thenReturn(votes);

        ResultVotesModel result = votingService.result(topicId);

        assertEquals(topicId, result.getTopicId());
        assertEquals("Test Topic", result.getTopicName());
        assertEquals(2, result.getVoteYes());
        assertEquals(1, result.getVoteNo());
    }

    @Test
    void vote_shouldSaveVoteAndValidate() {
        VoteModel voteModel = new VoteModel(null, true, UUID.randomUUID(), CPF);
        TopicModel topicModel = new TopicModel();
        topicModel.setOpen(true);

        when(queryTopicUseCase.findByIdOrThrowsNotFound(voteModel.getTopicId())).thenReturn(topicModel);
        when(voteRepository.findByCpfAndTopicId(voteModel.getCpf(), voteModel.getTopicId())).thenReturn(List.of());
        when(voteRepository.save(voteModel)).thenReturn(voteModel);

        VoteModel savedVote = votingService.vote(voteModel);

        assertEquals(voteModel, savedVote);
        verify(queryTopicUseCase).findByIdOrThrowsNotFound(voteModel.getTopicId());
        verify(queryAssociateUseCase).validateAssociateCanVote(voteModel.getCpf());
        verify(voteRepository).save(voteModel);
    }

    @Test
    void vote_shouldThrowExceptionIfTopicIsClosed() {
        VoteModel voteModel = new VoteModel(null, true, UUID.randomUUID(), CPF);
        TopicModel topicModel = new TopicModel();
        topicModel.setOpen(false);

        when(queryTopicUseCase.findByIdOrThrowsNotFound(voteModel.getTopicId())).thenReturn(topicModel);

        assertThrows(VoteCannotBeCastException.class, () -> votingService.vote(voteModel));
        verify(queryAssociateUseCase, never()).validateAssociateCanVote(anyString());
        verify(voteRepository, never()).save(any());
    }

    @Test
    void vote_shouldThrowExceptionIfAlreadyVoted() {
        VoteModel voteModel = new VoteModel(null, true, UUID.randomUUID(), CPF);
        TopicModel topicModel = new TopicModel();
        topicModel.setOpen(true);
        List<VoteModel> existingVote = List.of(new VoteModel());

        when(queryTopicUseCase.findByIdOrThrowsNotFound(voteModel.getTopicId())).thenReturn(topicModel);
        when(voteRepository.findByCpfAndTopicId(voteModel.getCpf(), voteModel.getTopicId())).thenReturn(existingVote);

        assertThrows(EntityAlreadyExist.class, () -> votingService.vote(voteModel));
    }
}