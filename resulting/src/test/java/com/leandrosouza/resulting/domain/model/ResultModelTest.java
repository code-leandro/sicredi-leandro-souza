package com.leandrosouza.resulting.domain.model;

import com.leandrosouza.resulting.domain.model.ResultModel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ResultModelTest {

    @Test
    void testResultModelCreation() {
        UUID topicId = UUID.randomUUID();
        String topicName = "Test Topic";
        Integer voteYes = 10;
        Integer voteNo = 5;

        ResultModel resultModel = new ResultModel(topicId, topicName, voteYes, voteNo);

        assertEquals(topicId, resultModel.getTopicId());
        assertEquals(topicName, resultModel.getTopicName());
        assertEquals(voteYes, resultModel.getVoteYes());
        assertEquals(voteNo, resultModel.getVoteNo());
    }

    @Test
    void testResultModelNoArgsConstructor() {
        ResultModel resultModel = new ResultModel();

        assertNull(resultModel.getTopicId());
        assertNull(resultModel.getTopicName());
        assertNull(resultModel.getVoteYes());
        assertNull(resultModel.getVoteNo());
    }

    @Test
    void testResultModelSetters() {
        UUID topicId = UUID.randomUUID();
        String topicName = "Test Topic";
        Integer voteYes = 10;
        Integer voteNo = 5;

        ResultModel resultModel = new ResultModel();
        resultModel.setTopicId(topicId);
        resultModel.setTopicName(topicName);
        resultModel.setVoteYes(voteYes);
        resultModel.setVoteNo(voteNo);

        assertEquals(topicId, resultModel.getTopicId());
        assertEquals(topicName, resultModel.getTopicName());
        assertEquals(voteYes, resultModel.getVoteYes());
        assertEquals(voteNo, resultModel.getVoteNo());
    }

    @Test
    void testEqualsAndHashCode() {
        UUID topicId1 = UUID.randomUUID();
        UUID topicId2 = UUID.randomUUID(); // Different UUID

        ResultModel resultModel1 = new ResultModel(topicId1, "Test", 10, 5);
        ResultModel resultModel2 = new ResultModel(topicId1, "Test", 10, 5); // Same UUID
        ResultModel resultModel3 = new ResultModel(topicId2, "Test", 10, 5); // Different UUID

        assertEquals(resultModel1, resultModel2);
        assertNotEquals(resultModel1, resultModel3);

        assertEquals(resultModel1.hashCode(), resultModel2.hashCode());
        assertNotEquals(resultModel1.hashCode(), resultModel3.hashCode());
    }


    @Test
    void testToString() {
        UUID topicId = UUID.randomUUID();
        String topicName = "Test Topic";
        Integer voteYes = 10;
        Integer voteNo = 5;

        ResultModel resultModel = new ResultModel(topicId, topicName, voteYes, voteNo);

        String expectedToString = "ResultModel(topicId=" + topicId + ", topicName=" + topicName + ", voteYes=" + voteYes + ", voteNo=" + voteNo + ")";
        assertEquals(expectedToString, resultModel.toString());
    }
}