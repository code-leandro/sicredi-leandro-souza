package com.leandrosouza.associate.adapters.in.dto.response;

import java.util.UUID;

public record AssociateOutDTO (
        UUID uuid,
        String cpf,
        Boolean ableToVote
){}
