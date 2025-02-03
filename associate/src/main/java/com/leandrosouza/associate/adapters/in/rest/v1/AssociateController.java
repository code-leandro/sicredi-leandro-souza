package com.leandrosouza.associate.adapters.in.rest.v1;

import com.leandrosouza.associate.adapters.in.dto.response.AssociateOutDTO;
import com.leandrosouza.associate.adapters.mapper.AssociateMapper;
import com.leandrosouza.associate.adapters.in.dto.request.AssociateInDTO;
import com.leandrosouza.associate.adapters.in.dto.response.ResultDTO;
import com.leandrosouza.associate.application.port.in.CreateAssociateUseCase;
import com.leandrosouza.associate.application.port.in.QueryAssociateUseCase;
import com.leandrosouza.associate.domain.exception.CPFInvalidQueryException;
import com.leandrosouza.associate.domain.model.AssociateModel;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/associate")
@Slf4j
public class AssociateController implements AssociateControllerProtocol {

    @Autowired
    private CreateAssociateUseCase createAssociateUseCase;

    @Autowired
    private QueryAssociateUseCase queryAssociateUseCase;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AssociateOutDTO> create(@Valid @RequestBody AssociateInDTO associateInDTO) {

        log.info("[AssociateController > create] cpf: {}", associateInDTO.cpf());
        AssociateModel associateModel = AssociateMapper.toDomain(associateInDTO);
        associateModel = createAssociateUseCase.create(associateModel);
        AssociateOutDTO associateOutDTO = AssociateMapper.toDTOOut(associateModel);
        return ResponseEntity.created(URI.create("/" + associateModel.getUuid())).body(associateOutDTO);
    }

    @Override
    public ResponseEntity<ResultDTO> queryStatus(@PathVariable String cpf) {

        log.info("[AssociateController > queryStatus] cpf: {}", cpf);
        AssociateModel associateModel = queryAssociateUseCase.query(cpf)
                .orElseThrow(CPFInvalidQueryException::new);
        return ResponseEntity.ok(new ResultDTO(associateModel.getStatus()));
    }
}
