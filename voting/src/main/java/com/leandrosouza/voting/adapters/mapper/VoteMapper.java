package com.leandrosouza.voting.adapters.mapper;

import com.leandrosouza.voting.adapters.in.dto.request.VoteInDTO;
import com.leandrosouza.voting.adapters.in.dto.response.VoteOutDTO;
import com.leandrosouza.voting.adapters.out.persistence.VoteEntity;
import com.leandrosouza.voting.domain.model.VoteModel;

public class VoteMapper {


    public static VoteModel toVoteModel(VoteInDTO voteInDTO) {

        return VoteModel.builder()
                .option(voteInDTO.option())
                .topicId(voteInDTO.topicId())
                .cpf(voteInDTO.cpf())
                .build();
    }

    public static VoteOutDTO toVoteOutDTO(VoteModel voteModel) {

        return new VoteOutDTO(
                voteModel.getId(),
                voteModel.isOption(),
                voteModel.getTopicId(),
                voteModel.getCpf()
                );
    }

    public static VoteModel toVoteModel(VoteEntity voteEntity) {

        return VoteModel.builder()
                .id(voteEntity.getId())
                .option(voteEntity.isOptionVote())
                .topicId(voteEntity.getTopicId())
                .cpf(voteEntity.getCpf())
                .build();
    }

    public static VoteEntity toVoteEntity(VoteModel voteModel) {

        return VoteEntity.builder()
                .id(voteModel.getId())
                .optionVote(voteModel.isOption())
                .topicId(voteModel.getTopicId())
                .cpf(voteModel.getCpf())
                .build();
    }
}
