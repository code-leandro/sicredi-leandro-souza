package com.leandrosouza.associate.adapters.in.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociateInDTO {

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "CPF must have exactly 11 characters")
    private String cpf;

    @NotNull
    private Boolean ableToVote;
}
