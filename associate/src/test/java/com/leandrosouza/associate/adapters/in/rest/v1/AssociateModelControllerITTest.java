package com.leandrosouza.associate.adapters.in.rest.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leandrosouza.associate.adapters.in.dto.request.AssociateInDTO;
import com.leandrosouza.associate.adapters.out.persistence.AssociateEntity;
import com.leandrosouza.associate.adapters.out.persistence.AssociateMapper;
import com.leandrosouza.associate.application.service.AssociateService;
import com.leandrosouza.associate.domain.model.AssociateModel;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AssociateModelControllerITTest {

    public static final String CPF_VALID_1 = "11727451023";
    public static final String CPF_VALID_2 = "26467678036";
    public static final String CPF_VALID_3 = "59157362033";
    public static final String CPF_INVALID = "11122233399";

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AssociateService associateService;

    @Test
    void createAssociate_success() throws Exception {
        AssociateInDTO associateInDTO = new AssociateInDTO(CPF_VALID_3, true);

        mockMvc.perform(post("/v1/associate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(associateInDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void createAssociate_invalidInput() throws Exception {
        AssociateInDTO associateInDTO = new AssociateInDTO("", true);

        mockMvc.perform(post("/v1/associate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(associateInDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createAssociate_invalidCPF() throws Exception {
        AssociateInDTO associateInDTO = new AssociateInDTO(CPF_INVALID, true);

        mockMvc.perform(post("/v1/associate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(associateInDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void queryStatus_success() throws Exception {

        AssociateModel associateModel1 = new AssociateModel(null, CPF_VALID_1, true);
        AssociateModel associateModel2 = new AssociateModel(null, CPF_VALID_2, false);
        AssociateEntity entity1 = AssociateMapper.toEntity(associateModel1);
        AssociateEntity entity2 = AssociateMapper.toEntity(associateModel2);
        entityManager.persist(entity1);
        entityManager.persist(entity2);
        entityManager.flush();

        mockMvc.perform(get("/v1/associate/status/" + CPF_VALID_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ABLE_TO_VOTE"));

        mockMvc.perform(get("/v1/associate/status/" + CPF_VALID_2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UNABLE_TO_VOTE"));
    }

    @Test
    void queryStatus_notFound() throws Exception {
        mockMvc.perform(get("/v1/associate/status/" + CPF_VALID_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
