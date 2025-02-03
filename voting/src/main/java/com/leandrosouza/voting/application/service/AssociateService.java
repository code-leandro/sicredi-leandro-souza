package com.leandrosouza.voting.application.service;

import com.leandrosouza.voting.application.port.in.associate.QueryAssociateUseCase;
import com.leandrosouza.voting.application.port.out.AssociateRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssociateService implements QueryAssociateUseCase {

    @Autowired
    AssociateRemote associateRemote;

    @Override
    public void validateAssociateCanVote(String cpf) {
        associateRemote.validateAssociateCanVote(cpf);
    }
}
