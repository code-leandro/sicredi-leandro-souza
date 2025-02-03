package com.leandrosouza.voting.adapters.in.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicInDTO {

    @NotBlank
    private String name;
    private Integer duration;
}
