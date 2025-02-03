package com.leandrosouza.resulting.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultModel {

    private UUID topicId;
    private String topicName;
    private Integer voteYes;
    private Integer voteNo;
}
