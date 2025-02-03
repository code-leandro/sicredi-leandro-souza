package com.leandrosouza.resulting.adapters.mapper;

import com.leandrosouza.resulting.adapters.in.dto.ResultDTO;
import com.leandrosouza.resulting.adapters.mapper.ResultMapper;
import com.leandrosouza.resulting.domain.model.ResultModel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ResultMapperTest {

    @Test
    void toDomain_successfulMapping() {

        UUID topicId = UUID.randomUUID();
        ResultDTO resultDTO = new ResultDTO(
                topicId,
                "Test Topic",
                10,
                5
        );


        ResultModel resultModel = ResultMapper.toDomain(resultDTO);

        assertNotNull(resultModel);
        assertEquals(topicId, resultModel.getTopicId());
        assertEquals("Test Topic", resultModel.getTopicName());
        assertEquals(10, resultModel.getVoteYes());
        assertEquals(5, resultModel.getVoteNo());
    }
}