package com.leandrosouza.voting.application.port.in.vote;

import com.leandrosouza.voting.domain.model.VoteModel;

public interface VoteUseCase {
    VoteModel vote(VoteModel voteModel);
}
