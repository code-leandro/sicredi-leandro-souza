package com.leandrosouza.voting.application.service;

import com.leandrosouza.voting.application.port.in.associate.QueryAssociateUseCase;
import com.leandrosouza.voting.application.port.in.result.QueryResultVotingUseCase;
import com.leandrosouza.voting.application.port.in.topic.QueryTopicUseCase;
import com.leandrosouza.voting.application.port.in.vote.VoteUseCase;
import com.leandrosouza.voting.application.port.out.VoteRepository;
import com.leandrosouza.voting.domain.exception.EntityAlreadyExist;
import com.leandrosouza.voting.domain.exception.VoteCannotBeCastException;
import com.leandrosouza.voting.domain.model.ResultVotesModel;
import com.leandrosouza.voting.domain.model.TopicModel;
import com.leandrosouza.voting.domain.model.VoteModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class VotingService implements QueryResultVotingUseCase, VoteUseCase {

    public static final String VOTE_CANNOT_BE_CAST_TOPIC_IS_CLOSED = "Vote cannot be cast: topic is closed";

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private QueryTopicUseCase queryTopicUseCase;

    @Autowired
    private QueryAssociateUseCase queryAssociateUseCase;

    @Override
    public ResultVotesModel result(UUID topicId) {
        TopicModel topicModel = queryTopicUseCase.findByIdOrThrowsNotFound(topicId);
        List<VoteModel> listVotes = voteRepository.findByTopicId(topicId);
        int countYes = listVotes.stream().filter(VoteModel::isYes).toList().size();
        int countNo = listVotes.stream().filter(VoteModel::isFalse).toList().size();
        return ResultVotesModel
                .builder()
                .voteYes(countYes)
                .voteNo(countNo)
                .topicId(topicId)
                .topicName(topicModel.getName())
                .build();
    }

    @Override
    public VoteModel vote(VoteModel voteModel) {
        log.info("[VotingService > vote] cpf: {}, topicId: {}, option: {}",
                voteModel.getCpf(), voteModel.getTopicId(), voteModel.isOption());
        validateTopic(voteModel.getTopicId());
        validateCPFToVote(voteModel);
        validateAlreadyVote(voteModel);
        return voteRepository.save(voteModel);
    }

    private void validateAlreadyVote(VoteModel voteModel) {

        log.info("[VotingService > validateAlreadyVote] cpf: {}, topicId: {}", voteModel.getCpf(), voteModel.getTopicId());
        List<VoteModel> listVoteEntityAlreadyExist = voteRepository.findByCpfAndTopicId(voteModel.getCpf(), voteModel.getTopicId());
        if (! listVoteEntityAlreadyExist.isEmpty()) {
            String error = String.format("Vote for this CPF %s and this topic %s already done!",
                    voteModel.getCpf(),
                    voteModel.getTopicId());
            throw new EntityAlreadyExist(error);
        }
    }

    private void validateCPFToVote(VoteModel voteModel) {
        log.info("[VotingService > validateCPFToVote] cpf: {}, topicId: {}", voteModel.getCpf(), voteModel.getTopicId());
        queryAssociateUseCase.validateAssociateCanVote(voteModel.getCpf());
    }

    public void validateTopic(UUID topicId) {
        log.info("[VotingService > validateTopic] topicId: {}", topicId);
        TopicModel topicModel = queryTopicUseCase.findByIdOrThrowsNotFound(topicId);
        if (topicModel.isClosed()) {
            throw new VoteCannotBeCastException(VOTE_CANNOT_BE_CAST_TOPIC_IS_CLOSED);
        }
    }
}
