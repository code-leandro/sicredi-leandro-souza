package com.leandrosouza.resulting.adapters.in.dto;

import java.util.UUID;

public record ResultDTO (
    UUID topicId,
    String topicName,
    Integer voteYes,
    Integer voteNo
){}
