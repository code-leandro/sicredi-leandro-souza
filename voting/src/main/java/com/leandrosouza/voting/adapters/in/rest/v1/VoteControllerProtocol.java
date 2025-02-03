package com.leandrosouza.voting.adapters.in.rest.v1;

import com.leandrosouza.voting.adapters.in.dto.request.VoteInDTO;
import com.leandrosouza.voting.adapters.in.dto.response.TopicOutDTO;
import com.leandrosouza.voting.adapters.in.dto.response.VoteOutDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/votes")
public interface VoteControllerProtocol {

    @Operation(summary = "Cast a vote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vote cast successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VoteOutDTO.class))),
            @ApiResponse(responseCode = "200", description = "Vote for CPF and topic already done"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "409", description = "Vote cannot be cast (e.g., topic closed)", content = @Content)
    })
    @PostMapping
    ResponseEntity<VoteOutDTO> vote(@RequestBody @Valid VoteInDTO voteInDTO) throws Exception;
}