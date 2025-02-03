package com.leandrosouza.voting.application.port.in.result;

import com.leandrosouza.voting.domain.model.ResultVotesModel;

import java.util.UUID;

public interface QueryResultVotingUseCase {
    ResultVotesModel result(UUID topicId);
}
