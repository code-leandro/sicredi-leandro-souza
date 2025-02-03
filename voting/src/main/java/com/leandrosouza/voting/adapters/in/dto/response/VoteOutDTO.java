package com.leandrosouza.voting.adapters.in.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteOutDTO {

    private UUID id;
    private boolean option;
    private UUID topicId;
    private String cpf;

}
