package com.leandrosouza.voting.adapters.in.rest.v1;

import com.leandrosouza.voting.adapters.in.dto.request.VoteInDTO;
import com.leandrosouza.voting.adapters.in.dto.response.VoteOutDTO;
import com.leandrosouza.voting.adapters.mapper.VoteMapper;
import com.leandrosouza.voting.application.port.in.vote.VoteUseCase;
import com.leandrosouza.voting.domain.model.VoteModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/votes")
class VoteController implements VoteControllerProtocol {
    @Autowired
    private VoteUseCase voteUseCase;

    @Override
    public ResponseEntity<VoteOutDTO> vote(@RequestBody @Valid VoteInDTO voteInDTO) throws Exception {
        VoteModel voteModel = VoteMapper.toVoteModel(voteInDTO);
        voteModel = voteUseCase.vote(voteModel);
        VoteOutDTO voteOutDTO = VoteMapper.toVoteOutDTO(voteModel);
        return ResponseEntity
                .created(URI.create("/" + voteModel.getId()))
                .body(voteOutDTO);
    }
}
