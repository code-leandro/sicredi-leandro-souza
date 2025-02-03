package com.leandrosouza.voting.adapters.in.dto.publisher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultDTO {

    private UUID topicId;
    private String topicName;
    private Integer voteYes;
    private Integer voteNo;
}
