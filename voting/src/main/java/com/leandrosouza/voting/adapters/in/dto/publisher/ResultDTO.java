package com.leandrosouza.voting.adapters.in.dto.publisher;

import java.util.UUID;

public record ResultDTO (

    UUID topicId,
    String topicName,
    Integer voteYes,
    Integer voteNo
){}
