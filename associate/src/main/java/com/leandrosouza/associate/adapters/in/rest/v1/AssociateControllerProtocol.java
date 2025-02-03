package com.leandrosouza.associate.adapters.in.rest.v1;

import com.leandrosouza.associate.adapters.in.dto.request.AssociateInDTO;
import com.leandrosouza.associate.adapters.in.dto.response.AssociateOutDTO;
import com.leandrosouza.associate.adapters.in.dto.response.ResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface AssociateControllerProtocol {

    @Operation(summary = "Create an associate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema( implementation = AssociateOutDTO.class))),
            @ApiResponse(responseCode = "409", description = "Entity already exist (same CPF)", content = @Content)
    })
    @PostMapping
    ResponseEntity<AssociateOutDTO> create(@Valid @RequestBody AssociateInDTO associateInDTO);

    @Operation(summary = "Query an Associate Status")
    @ApiResponse(
            responseCode = "200", description = "OK",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                               schema = @Schema( implementation = ResultDTO.class)))
    @ApiResponse(responseCode = "404", description = "CPF not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping("/status/{cpf}")
    ResponseEntity<ResultDTO> queryStatus(
            @Parameter(description = "CPF of associate (only numbers)", example = "06623685090")
            @PathVariable String cpf
    );
}
