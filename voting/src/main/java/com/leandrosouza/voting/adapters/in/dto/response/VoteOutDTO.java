package com.leandrosouza.voting.adapters.in.dto.response;

import java.util.UUID;

public record VoteOutDTO (

    UUID id,
    boolean option,
    UUID topicId,
    String cpf
){}
