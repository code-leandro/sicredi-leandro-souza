package com.leandrosouza.voting.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultVotesModel {
    private UUID topicId;
    private String topicName;
    private Integer voteYes;
    private Integer voteNo;
}
