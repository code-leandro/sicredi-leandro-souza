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
        ResultDTO resultDTO = new ResultDTO();
        UUID topicId = UUID.randomUUID();
        resultDTO.setTopicId(topicId);
        resultDTO.setTopicName("Test Topic");
        resultDTO.setVoteYes(10);
        resultDTO.setVoteNo(5);

        ResultModel resultModel = ResultMapper.toDomain(resultDTO);

        assertNotNull(resultModel);
        assertEquals(topicId, resultModel.getTopicId());
        assertEquals("Test Topic", resultModel.getTopicName());
        assertEquals(10, resultModel.getVoteYes());
        assertEquals(5, resultModel.getVoteNo());
    }

    @Test
    void toDomain_nullInput() {
        ResultDTO resultDTO = null;

        ResultModel resultModel = ResultMapper.toDomain(resultDTO);

        assertNull(resultModel);
    }

    @Test
    void toDomain_emptyInput() {
        ResultDTO resultDTO = new ResultDTO();

        ResultModel resultModel = ResultMapper.toDomain(resultDTO);

        assertNotNull(resultModel);
        assertNull(resultModel.getTopicId());
        assertNull(resultModel.getTopicName());
        assertNull(resultModel.getVoteYes());
        assertNull(resultModel.getVoteNo());
    }
}