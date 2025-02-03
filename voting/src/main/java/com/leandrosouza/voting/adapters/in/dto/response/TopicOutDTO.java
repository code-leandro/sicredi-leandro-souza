package com.leandrosouza.voting.adapters.in.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record TopicOutDTO (

    UUID id,
    String name,
    Integer duration,
    boolean open,
    LocalDateTime startTime,
    LocalDateTime predictedEndTime,
    LocalDateTime effectiveEndTime
){}
