package com.leandrosouza.associate.application.service;

import com.leandrosouza.associate.application.port.out.AssociateRepositoryPort;
import com.leandrosouza.associate.application.service.AssociateService;
import com.leandrosouza.associate.domain.exception.CPFInvalidToStoreException;
import com.leandrosouza.associate.domain.exception.EntityAlreadyExistException;
import com.leandrosouza.associate.domain.model.AssociateModel;
import com.leandrosouza.associate.domain.util.CPFUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssociateServiceTest {

    @InjectMocks
    private AssociateService service;

    @Mock
    private AssociateRepositoryPort associateRepositoryPort;

    private static final UUID UUID_1 = UUID.randomUUID();
    private static final String CPF_VALID = "69699829044";
    private static final String CPF_INVALID = "12345678901";
    private static final AssociateModel ASSOCIATE_MODEL = new AssociateModel(UUID_1, CPF_VALID, true);

    @Test
    void create_shouldSaveAssociate_whenCPFIsValidAndDoesNotExist() {
        when(associateRepositoryPort.query(CPF_VALID)).thenReturn(Optional.empty());
        when(associateRepositoryPort.save(ASSOCIATE_MODEL)).thenReturn(ASSOCIATE_MODEL);

        AssociateModel createdAssociate = service.create(ASSOCIATE_MODEL);

        assertNotNull(createdAssociate);
        assertEquals(ASSOCIATE_MODEL.getCpf(), createdAssociate.getCpf());
        assertEquals(ASSOCIATE_MODEL.isAbleToVote(), createdAssociate.isAbleToVote());
        verify(associateRepositoryPort).save(ASSOCIATE_MODEL);
    }

    @Test
    void create_shouldThrowCPFInvalidToStoreException_whenCPFIsInvalid() {
        assertThrows(CPFInvalidToStoreException.class, () -> service.create(new AssociateModel(null, CPF_INVALID, true)));
        verify(associateRepositoryPort, never()).save(any());
    }

    @Test
    void create_shouldThrowEntityAlreadyExistException_whenCPFAlreadyExists() {
        when(associateRepositoryPort.query(CPF_VALID)).thenReturn(Optional.of(ASSOCIATE_MODEL)); // CPF já existe
        assertThrows(EntityAlreadyExistException.class, () -> service.create(ASSOCIATE_MODEL));
        verify(associateRepositoryPort, never()).save(any());
    }

    @Test
    void query_shouldReturnAssociate_whenAssociateExists() {
        when(associateRepositoryPort.query(CPF_VALID)).thenReturn(Optional.of(ASSOCIATE_MODEL));

        Optional<AssociateModel> result = service.query(CPF_VALID);

        assertTrue(result.isPresent());
        assertEquals(ASSOCIATE_MODEL.getCpf(), result.get().getCpf());
        assertEquals(ASSOCIATE_MODEL.isAbleToVote(), result.get().isAbleToVote());
    }

    @Test
    void query_shouldReturnEmptyOptional_whenAssociateDoesNotExist() {
        when(associateRepositoryPort.query(CPF_VALID)).thenReturn(Optional.empty());
        Optional<AssociateModel> result = service.query(CPF_VALID);
        assertTrue(result.isEmpty());
    }

    @Test
    void query_shouldReturnEmptyOptional_whenCpfIsNull() {
        Optional<AssociateModel> result = service.query(null);
        assertTrue(result.isEmpty());
    }
}