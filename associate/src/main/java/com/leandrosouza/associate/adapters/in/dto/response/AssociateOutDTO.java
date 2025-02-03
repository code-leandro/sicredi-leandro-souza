package com.leandrosouza.associate.adapters.in.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociateOutDTO {
    private UUID uuid;
    private String cpf;
    private Boolean ableToVote;
}
