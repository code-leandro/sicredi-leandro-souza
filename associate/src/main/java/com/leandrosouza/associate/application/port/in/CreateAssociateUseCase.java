package com.leandrosouza.associate.application.port.in;

import com.leandrosouza.associate.domain.model.AssociateModel;

public interface CreateAssociateUseCase {
    public AssociateModel create(AssociateModel user);
}
