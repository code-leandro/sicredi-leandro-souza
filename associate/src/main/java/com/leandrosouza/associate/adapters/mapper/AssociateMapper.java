package com.leandrosouza.associate.adapters.mapper;

import com.leandrosouza.associate.adapters.in.dto.request.AssociateInDTO;
import com.leandrosouza.associate.adapters.in.dto.response.AssociateOutDTO;
import com.leandrosouza.associate.domain.model.AssociateModel;

public class AssociateMapper {
    public static AssociateInDTO toDTOIn(AssociateModel associateModel) {
        if (associateModel == null) return null;
        return new AssociateInDTO(associateModel.getCpf(), associateModel.isAbleToVote());
    }

    public static AssociateModel toDomain(AssociateInDTO associateInDTO) {
        if (associateInDTO == null) return null;
        return new AssociateModel(null, associateInDTO.cpf(), associateInDTO.ableToVote());
    }

    public static AssociateOutDTO toDTOOut(AssociateModel associateModel) {
        if (associateModel == null) return null;
        return new AssociateOutDTO(associateModel.getUuid(), associateModel.getCpf(), associateModel.isAbleToVote());
    }
}
