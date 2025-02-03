package com.leandrosouza.resulting.adapters.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leandrosouza.resulting.adapters.in.dto.ResultDTO;
import com.leandrosouza.resulting.adapters.mapper.ResultMapper;
import com.leandrosouza.resulting.application.port.in.ProcessResultUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResultListenerTest {

    @InjectMocks
    private ResultListener resultListener;

    @Mock
    private ProcessResultUseCase processResultUseCase;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testReceive_successfulDeserialization() throws Exception {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setTopicId(UUID.randomUUID());
        resultDTO.setTopicName("Test Topic");

        String json = objectMapper.writeValueAsString(resultDTO);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(json.getBytes(), messageProperties);

        resultListener.receive(message);
        verify(processResultUseCase).process(ResultMapper.toDomain(resultDTO));
    }

    @Test
    void testReceive_deserializationError() throws Exception {

        String invalidJson = "invalid json";
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(invalidJson.getBytes(), messageProperties);

        resultListener.receive(message);

        verify(processResultUseCase, never()).process(any());
    }

    @Test
    void testReceive_invalidContentType() throws Exception {
        String json = "{\"key\": \"value\"}";
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain"); // Content type diferente de application/json
        Message message = new Message(json.getBytes(), messageProperties);

        resultListener.receive(message);

        verify(processResultUseCase, never()).process(any()); // Garante que não foi processado
    }

    @Test
    void testReceive_emptyMessageBody() {

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message("".getBytes(), messageProperties); // Corpo da mensagem vazio

        resultListener.receive(message);

        verify(processResultUseCase, never()).process(any()); // Garante que não foi processado
    }
}