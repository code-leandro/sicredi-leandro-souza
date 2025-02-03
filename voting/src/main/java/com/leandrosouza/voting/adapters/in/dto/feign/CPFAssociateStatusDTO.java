package com.leandrosouza.voting.adapters.in.dto.feign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CPFAssociateStatusDTO {
    private String status;
}
