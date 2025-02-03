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
public class VoteModel {

    private UUID id;
    private boolean option;
    private UUID topicId;
    private String cpf;

    public boolean isYes(){
        return isOption();
    }

    public boolean isFalse (){
        return ! isOption();
    }
}
