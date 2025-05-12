package ru.dhabits.fixchaos.planning.usecase.direction.createdirection;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.dhabits.fixchaos.planning.config.TestConfigHelper;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.domain.repository.DirectionRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.createdirection.request.DirectionRequestDto;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dhabits.fixchaos.planning.commons.TestData.CODE;
import static ru.dhabits.fixchaos.planning.commons.TestData.NAME;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class CreateDirectionUseCaseTest extends TestConfigHelper {

    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void execute_Successful_WithNoParentDirection() throws Exception {
        DirectionRequestDto directionRequestDto = new DirectionRequestDto().setCode("CODE").setName("NAME");

        mockMvc.perform(post("/v1/direction").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(directionRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(CODE))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.parentDirection").isEmpty());
    }

    @Test
    public void execute_Successful_WithParentDirection() throws Exception {
        Direction parentDirection = directionRepository.save(new Direction().setCode("PARENT_CODE").setName("PARENT_NAME"));
        DirectionRequestDto directionRequestDto = new DirectionRequestDto()
                .setCode("CODE")
                .setName("NAME")
                .setParentId(parentDirection.getId());

        mockMvc.perform(post("/v1/direction").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(directionRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(CODE))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.parentDirection.code").value("PARENT_CODE"))
                .andExpect(jsonPath("$.parentDirection.name").value("PARENT_NAME"))
                .andExpect(jsonPath("$.parentDirection.id").value(parentDirection.getId().toString()));
    }

    @Test
    public void executeWithException_WithParentThatDoesNotExist() throws Exception {
        DirectionRequestDto directionRequestDto = new DirectionRequestDto()
                .setCode("CODE")
                .setName("NAME")
                .setParentId(UUID.fromString("3cc46125-0fe8-404a-9dd4-be9f29a3052f"));

        mockMvc.perform(post("/v1/direction").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(directionRequestDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(
                        result -> Assertions.assertInstanceOf(
                                EntityAlreadyExistsOrDoesNotExistException.class,
                                result.getResolvedException()
                        )
                );
    }
}
