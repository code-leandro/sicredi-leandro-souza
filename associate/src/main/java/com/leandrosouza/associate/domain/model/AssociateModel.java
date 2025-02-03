package com.leandrosouza.associate.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociateModel {

    private UUID uuid;
    private String cpf;
    private boolean ableToVote;

    public static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
    public static final String UNABLE_TO_VOTE = "UNABLE_TO_VOTE";

    public String getStatus() {
        return isAbleToVote() ? ABLE_TO_VOTE : UNABLE_TO_VOTE;
    }
}
