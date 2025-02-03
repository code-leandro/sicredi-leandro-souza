package com.leandrosouza.resulting.adapters.in.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {

    private UUID topicId;
    private String topicName;
    private Integer voteYes;
    private Integer voteNo;
}
