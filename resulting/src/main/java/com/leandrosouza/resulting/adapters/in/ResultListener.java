package com.leandrosouza.resulting.adapters.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leandrosouza.resulting.adapters.in.dto.ResultDTO;
import com.leandrosouza.resulting.adapters.mapper.ResultMapper;
import com.leandrosouza.resulting.application.port.in.ProcessResultUseCase;

@Component
@Slf4j
public class ResultListener {

    @Autowired
    ProcessResultUseCase processResultUseCase;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receive(Message message) {

        try {
            String json = new String(message.getBody());
            ResultDTO resultDTO = objectMapper.readValue(json, ResultDTO.class);
            processResultUseCase.process(ResultMapper.toDomain(resultDTO));
        } catch (Exception e) {
            log.error("[ResultListener > receive] Error when trying deserializer message: " + e.getMessage());
        }
    }

}
