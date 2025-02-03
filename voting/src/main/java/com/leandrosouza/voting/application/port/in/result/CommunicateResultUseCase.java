package com.leandrosouza.voting.application.port.in.result;

import java.util.UUID;

public interface CommunicateResultUseCase {
    void sendResult(UUID topicId);
}
