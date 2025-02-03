package com.leandrosouza.voting.application.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class StandardError {

    private LocalDateTime time;
    private Integer status;
    private String error;
    private String path;
}
