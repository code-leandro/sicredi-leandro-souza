package com.leandrosouza.voting.adapters.in.rest.v1;
import com.leandrosouza.voting.adapters.in.dto.request.TopicInDTO;
import com.leandrosouza.voting.adapters.in.dto.response.TopicOutDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/v1/topics")
public interface TopicControllerProtocol {

    @Operation(summary = "Create a new topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Topic created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TopicOutDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    ResponseEntity<TopicOutDTO> create(@RequestBody @Valid TopicInDTO topicInDTO);

    @Operation(summary = "Send result of a topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Result sent successfully"),
            @ApiResponse(responseCode = "404", description = "Topic not found")
    })
    @PostMapping("/send/{topicId}")
    ResponseEntity<Void> send(@PathVariable UUID topicId);

    @Operation(summary = "Open a topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Topic opened successfully"),
            @ApiResponse(responseCode = "409", description = "Topic cannot be opened", content = @Content),
            @ApiResponse(responseCode = "404", description = "Topic not found")
    })
    @PostMapping("/open/{id}")
    ResponseEntity<Void> openTopic(@PathVariable UUID id);

    @Operation(summary = "Find all topics with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of topics returned successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TopicOutDTO.class)))
    })
    @GetMapping("/query")
    ResponseEntity<List<TopicOutDTO>> findAll(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize);
}