package com.leandrosouza.voting.adapters.mapper;

import com.leandrosouza.voting.adapters.in.dto.publisher.ResultDTO;
import com.leandrosouza.voting.domain.model.ResultVotesModel;

public class ResultMapper {

    public static ResultDTO toResultDTO(ResultVotesModel resultVotesModel) {
        return new ResultDTO(
                resultVotesModel.getTopicId(),
                resultVotesModel.getTopicName(),
                resultVotesModel.getVoteYes(),
                resultVotesModel.getVoteNo());
    }
}
