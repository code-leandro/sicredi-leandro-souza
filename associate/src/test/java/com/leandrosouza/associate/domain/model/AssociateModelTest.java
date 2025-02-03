package com.leandrosouza.associate.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AssociateModelTest {

    public static final UUID UUID = java.util.UUID.randomUUID();
    public static final String CPF = "12345678900";

    @Test
    void getStatus_shouldReturnABLE_TO_VOTE_whenAbleToVoteIsTrue() {
        AssociateModel associate = new AssociateModel(UUID, CPF, true);
        assertEquals(AssociateModel.ABLE_TO_VOTE, associate.getStatus());
    }

    @Test
    void getStatus_shouldReturnUNABLE_TO_VOTE_whenAbleToVoteIsFalse() {
        AssociateModel associate = new AssociateModel(UUID, CPF, false);
        assertEquals(AssociateModel.UNABLE_TO_VOTE, associate.getStatus());
    }
}