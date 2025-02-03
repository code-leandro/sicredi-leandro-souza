package com.leandrosouza.resulting.application.port.in;

import com.leandrosouza.resulting.domain.model.ResultModel;

public interface ProcessResultUseCase {
    void process(ResultModel resultModel);
}
