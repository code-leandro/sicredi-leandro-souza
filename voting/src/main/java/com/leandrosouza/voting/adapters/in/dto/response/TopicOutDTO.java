package com.leandrosouza.voting.adapters.in.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicOutDTO {

    private UUID id;
    private String name;
    private Integer duration;
    private boolean open;
    private LocalDateTime startTime;
    private LocalDateTime predictedEndTime;
    private LocalDateTime effectiveEndTime;
}
