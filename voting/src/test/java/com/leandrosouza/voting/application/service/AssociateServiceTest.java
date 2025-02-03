package com.leandrosouza.voting.application.service;

import com.leandrosouza.voting.application.port.in.associate.QueryAssociateUseCase;
import com.leandrosouza.voting.application.service.AssociateService;
import com.leandrosouza.voting.application.port.out.AssociateRemote;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssociateServiceTest {

    @InjectMocks
    private AssociateService associateService;

    @Mock
    private AssociateRemote associateRemote;

    @Test
    void validateAssociateCanVote_shouldCallAssociateRemote() {
        String cpf = "12345678900";

        associateService.validateAssociateCanVote(cpf);

        verify(associateRemote).validateAssociateCanVote(cpf);
    }

    @Test
    void validateAssociateCanVote_shouldHandleNullCPF() {
        associateService.validateAssociateCanVote(null);

        verify(associateRemote).validateAssociateCanVote(null);
    }

    @Test
    void validateAssociateCanVote_shouldHandleEmptyCPF() {
        associateService.validateAssociateCanVote("");

        verify(associateRemote).validateAssociateCanVote("");
    }
}