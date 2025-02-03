package com.leandrosouza.voting.adapters.in.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VoteInDTO (

    @NotNull
    Boolean option,
    @NotNull
    UUID topicId,
    @NotNull
    String cpf
){}
