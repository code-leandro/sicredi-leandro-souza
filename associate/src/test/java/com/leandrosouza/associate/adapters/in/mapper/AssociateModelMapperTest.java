package com.leandrosouza.associate.adapters.in.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.leandrosouza.associate.adapters.in.dto.request.AssociateInDTO;
import com.leandrosouza.associate.adapters.in.dto.response.AssociateOutDTO;
import com.leandrosouza.associate.adapters.mapper.AssociateMapper;
import com.leandrosouza.associate.domain.model.AssociateModel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class AssociateModelMapperTest {

    public static final UUID UUID_1 = UUID.randomUUID();
    public static final String CPF_1 = "12345678900";

    @Test
    void toDTOIn_shouldMapAssociateModelToAssociateInDTO() {
        AssociateModel associateModel = new AssociateModel(UUID_1, CPF_1, true);

        AssociateInDTO associateInDTO = AssociateMapper.toDTOIn(associateModel);

        assertEquals(associateModel.getCpf(), associateInDTO.cpf());
        assertEquals(associateModel.isAbleToVote(), associateInDTO.ableToVote());
    }

    @Test
    void toDomain_shouldMapAssociateInDTOToAssociateModel() {
        AssociateInDTO associateInDTO = new AssociateInDTO(CPF_1, true);

        AssociateModel associateModel = AssociateMapper.toDomain(associateInDTO);

        assertNull(associateModel.getUuid()); // UUID deve ser nulo na conversão para Domain
        assertEquals(associateInDTO.cpf(), associateModel.getCpf());
        assertEquals(associateInDTO.ableToVote(), associateModel.isAbleToVote());
    }

    @Test
    void toDTOOut_shouldMapAssociateModelToAssociateOutDTO() {
        AssociateModel associateModel = new AssociateModel(UUID_1, CPF_1, true);

        AssociateOutDTO associateOutDTO = AssociateMapper.toDTOOut(associateModel);

        assertEquals(associateModel.getUuid(), associateOutDTO.uuid());
        assertEquals(associateModel.getCpf(), associateOutDTO.cpf());
        assertEquals(associateModel.isAbleToVote(), associateOutDTO.ableToVote());
    }

    @Test
    void toDTOIn_shouldReturnNullWhenAssociateModelIsNull() {
        assertNull(AssociateMapper.toDTOIn(null));
    }

    @Test
    void toDomain_shouldReturnNullWhenAssociateInDTOIsNull() {
        assertNull(AssociateMapper.toDomain(null));
    }

    @Test
    void toDTOOut_shouldReturnNullWhenAssociateModelIsNull() {
        assertNull(AssociateMapper.toDTOOut(null));
    }

}