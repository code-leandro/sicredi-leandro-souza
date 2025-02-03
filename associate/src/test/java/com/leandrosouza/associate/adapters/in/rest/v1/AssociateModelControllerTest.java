package com.leandrosouza.associate.adapters.in.rest.v1;

import com.leandrosouza.associate.adapters.in.dto.request.AssociateInDTO;
import com.leandrosouza.associate.adapters.in.dto.response.AssociateOutDTO;
import com.leandrosouza.associate.adapters.in.dto.response.ResultDTO;
import com.leandrosouza.associate.application.port.in.CreateAssociateUseCase;
import com.leandrosouza.associate.application.port.in.QueryAssociateUseCase;
import com.leandrosouza.associate.domain.exception.CPFInvalidQueryException;
import com.leandrosouza.associate.domain.model.AssociateModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AssociateModelControllerTest {

    public static final String CPF_OK = "88034627089";
    public static final String CPF_NOT_FOUND = "12312312312";
    public static final UUID UUID_1 = UUID.randomUUID();

    @InjectMocks
    private AssociateController associateController;

    @Mock
    private CreateAssociateUseCase createAssociateUseCase;

    @Mock
    private QueryAssociateUseCase queryAssociateUseCase;

    @Test
    void createAssociate_success() {
        AssociateInDTO associateInDTO = new AssociateInDTO(CPF_OK, true);
        AssociateModel associateModel = new AssociateModel(UUID_1, CPF_OK, true);
        when(createAssociateUseCase.create(any(AssociateModel.class))).thenReturn(associateModel);
        ResponseEntity<AssociateOutDTO> associateOutDTOResponseEntity = associateController.create(associateInDTO);

        assertThat(associateOutDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(associateOutDTOResponseEntity.getHeaders().getLocation().toString()).isEqualTo("/" + UUID_1);
        assertThat(associateOutDTOResponseEntity.getBody().ableToVote()).isTrue();
        assertThat(associateOutDTOResponseEntity.getBody().uuid()).isEqualTo(UUID_1);
    }

    @Test
    void queryStatus_success() {
        AssociateModel associateModel = new AssociateModel(UUID_1, CPF_OK, true);
        ResultDTO expectedResult = new ResultDTO(associateModel.getStatus());
        when(queryAssociateUseCase.query(CPF_OK)).thenReturn(java.util.Optional.of(associateModel));
        ResponseEntity<ResultDTO> response = associateController.queryStatus(CPF_OK);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResult, response.getBody());
    }

    @Test
    void queryStatus_notFound() {
        when(queryAssociateUseCase.query(CPF_NOT_FOUND)).thenReturn(java.util.Optional.empty());
        assertThrows(CPFInvalidQueryException.class, () -> associateController.queryStatus(CPF_NOT_FOUND));
    }
}