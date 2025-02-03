package com.leandrosouza.associate.application.service;

import com.leandrosouza.associate.application.port.in.CreateAssociateUseCase;
import com.leandrosouza.associate.application.port.in.QueryAssociateUseCase;
import com.leandrosouza.associate.application.port.out.AssociateRepositoryPort;
import com.leandrosouza.associate.domain.exception.CPFInvalidToStoreException;
import com.leandrosouza.associate.domain.exception.EntityAlreadyExistException;
import com.leandrosouza.associate.domain.model.AssociateModel;
import com.leandrosouza.associate.domain.util.CPFUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AssociateService implements CreateAssociateUseCase, QueryAssociateUseCase {

    @Autowired
    private AssociateRepositoryPort associateRepositoryPort;

    @Override
    public AssociateModel create(AssociateModel associateModel) {
        log.info("[AssociateService > create] cpf: {}, ableToVote: {}", associateModel.getCpf(), associateModel.isAbleToVote());
        validateCPFToStore(associateModel.getCpf());
        validateCPFAlreadyExist(associateModel.getCpf());
        return associateRepositoryPort.save(associateModel);
    }

    @Override
    public Optional<AssociateModel> query(String cpf) {
        log.info("[AssociateService > query] cpf: {}", cpf);
        return associateRepositoryPort.query(cpf);
    }

    public void validateCPFToStore(String cpf) {
        log.info("[AssociateService > validateCPFToStore] cpf: {}", cpf);
        if (!CPFUtil.isValidCPF(cpf))
            throw new CPFInvalidToStoreException();
    }

    public void validateCPFAlreadyExist(String cpf) {
        log.info("[AssociateService > validateCPFAlreadyExist] cpf: {}", cpf);
        associateRepositoryPort.query(cpf).ifPresent(c -> {
            throw new EntityAlreadyExistException("CPF already exist");
        });
    }
}
