package com.leandrosouza.resulting.adapters.mapper;

import com.leandrosouza.resulting.adapters.in.dto.ResultDTO;
import com.leandrosouza.resulting.domain.model.ResultModel;

public class ResultMapper {

    public static ResultModel toDomain(ResultDTO resultDTO) {
        if (resultDTO == null) return null;
        return new ResultModel(
            resultDTO.topicId(),
            resultDTO.topicName(),
            resultDTO.voteYes(),
            resultDTO.voteNo());
    }

}
