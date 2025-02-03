package com.leandrosouza.voting.adapters.mapper;

import com.leandrosouza.voting.adapters.in.dto.publisher.ResultDTO;
import com.leandrosouza.voting.domain.model.ResultVotesModel;

public class ResultMapper {

    public static ResultDTO toResultDTO(ResultVotesModel resultVotesModel) {
        return ResultDTO
                .builder()
                .topicName(resultVotesModel.getTopicName())
                .topicId(resultVotesModel.getTopicId())
                .voteYes(resultVotesModel.getVoteYes())
                .voteNo(resultVotesModel.getVoteNo())
                .build();
    }
}
