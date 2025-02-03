package com.leandrosouza.voting.adapters.mapper;

import com.leandrosouza.voting.adapters.in.dto.response.TopicOutDTO;
import com.leandrosouza.voting.adapters.in.dto.request.TopicInDTO;
import com.leandrosouza.voting.domain.model.TopicModel;
import com.leandrosouza.voting.adapters.out.persistence.TopicEntity;

public class TopicMapper {

    public static TopicEntity toTopicEntity(TopicModel topicModel) {
        return TopicEntity.builder()
                .id(topicModel.getId())
                .name(topicModel.getName())
                .duration(topicModel.getDuration())
                .startTime(topicModel.getStartTime())
                .effectiveEndTime(topicModel.getEffectiveEndTime())
                .predictedEndTime(topicModel.getPredictedEndTime())
                .open(topicModel.isOpen())
                .build();
    }

    public static TopicModel toTopicModel(TopicInDTO topicInDTO) {

        return TopicModel.builder()
                .name(topicInDTO.name())
                .duration(topicInDTO.duration())
                .build();
    }

    public static TopicModel toTopicModel(TopicEntity topicEntity) {
        return TopicModel.builder()
                .id(topicEntity.getId())
                .name(topicEntity.getName())
                .duration(topicEntity.getDuration())
                .startTime(topicEntity.getStartTime())
                .effectiveEndTime(topicEntity.getEffectiveEndTime())
                .predictedEndTime(topicEntity.getPredictedEndTime())
                .open(topicEntity.isOpen())
                .build();
    }

    public static TopicOutDTO toTopicOutDTO(TopicModel topicModel) {
        return new TopicOutDTO(topicModel.getId(), topicModel.getName(), topicModel.getDuration(),
                topicModel.isOpen(), topicModel.getStartTime(), topicModel.getPredictedEndTime(),
                topicModel.getEffectiveEndTime());
    }
}
