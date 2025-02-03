package com.leandrosouza.voting.application.port.in.associate;

public interface QueryAssociateUseCase {
    void validateAssociateCanVote(String cpf);
}
