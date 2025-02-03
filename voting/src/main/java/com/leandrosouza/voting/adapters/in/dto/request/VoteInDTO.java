package com.leandrosouza.voting.adapters.in.dto.request;

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
public class VoteInDTO {

    @NotNull
    private Boolean option;

    @NotNull
    private UUID topicId;

    @NotNull
    private String cpf;

}
