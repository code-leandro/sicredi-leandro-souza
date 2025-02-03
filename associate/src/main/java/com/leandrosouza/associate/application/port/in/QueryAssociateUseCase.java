package com.leandrosouza.associate.application.port.in;

import com.leandrosouza.associate.domain.model.AssociateModel;

import java.util.Optional;

public interface QueryAssociateUseCase {
    public Optional<AssociateModel> query(String cpf);
}
