package com.leandrosouza.resulting.application.service;

import org.springframework.stereotype.Service;

import com.leandrosouza.resulting.application.port.in.ProcessResultUseCase;
import com.leandrosouza.resulting.domain.model.ResultModel;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessResultService implements ProcessResultUseCase {

    @Override
    public void process(ResultModel resultModel) {
        log.info("Result: {}", resultModel);
    }
    
}
