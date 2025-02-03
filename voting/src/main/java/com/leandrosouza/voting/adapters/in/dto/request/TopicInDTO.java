package com.leandrosouza.voting.adapters.in.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TopicInDTO (

    @NotBlank
    String name,
    Integer duration
){}
