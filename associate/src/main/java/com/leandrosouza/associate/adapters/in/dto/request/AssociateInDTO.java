package com.leandrosouza.associate.adapters.in.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record AssociateInDTO (

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "CPF must have exactly 11 characters")
    String cpf,

    @NotNull
    Boolean ableToVote
){};
