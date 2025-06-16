package ru.dhabits.fixchaos.planning.usecase.direction.updatedirection;

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
import ru.dhabits.fixchaos.planning.domain.repository.GoalRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.inbound.rest.direction.updatedirection.request.UpdateDirectionRequestDto;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class UpdateDirectionUseCaseTest extends TestConfigHelper {

    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void execute_Successful_WithNoParentDirection() throws Exception {
        Direction direction = new Direction()
                .setCode("CODE")
                .setName("NAME")
                .setDescription("DESCRIPTION");
        direction = directionRepository.save(direction);

        Optional<Direction> optionalDirection = directionRepository.findById(direction.getId());
        Assertions.assertTrue(optionalDirection.isPresent());
        Direction directionActual = optionalDirection.get();
        Assertions.assertEquals("CODE", directionActual.getCode());
        Assertions.assertEquals("NAME", directionActual.getName());
        Assertions.assertEquals("DESCRIPTION", directionActual.getDescription());
        UpdateDirectionRequestDto updateDirectionRequestDto = new UpdateDirectionRequestDto()
                .setCode("CODE2")
                .setName("NAME2")
                .setDescription("DESCRIPTION2");

        mockMvc.perform(
                put("/v1/direction/{id}", direction.getId())
                        .content(objectMapper.writeValueAsBytes(updateDirectionRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CODE2"))
                .andExpect(jsonPath("$.name").value("NAME2"))
                .andExpect(jsonPath("$.description").value("DESCRIPTION2"))
                .andExpect(jsonPath("$.parentDirection").isEmpty());

    }

    @Test
    public void execute_Successful_WithParentDirection() throws Exception {
        Direction parentDirection = new Direction()
                .setCode("PARENT_CODE")
                .setName("PARENT_NAME")
                .setDescription("PARENT_DESCRIPTION");
        parentDirection = directionRepository.save(parentDirection);

        Direction direction = new Direction()
                .setCode("CODE")
                .setName("NAME")
                .setDescription("DESCRIPTION")
                .setDirection(parentDirection);
        direction = directionRepository.save(direction);

        Direction newParentDirection = new Direction()
                .setCode("PARENT_CODE_2")
                .setName("PARENT_NAME_2")
                .setDescription("PARENT_DESCRIPTION_2");
        newParentDirection = directionRepository.save(newParentDirection);

        Optional<Direction> optionalDirection = directionRepository.findById(direction.getId());
        Assertions.assertTrue(optionalDirection.isPresent());
        Direction directionActual = optionalDirection.get();
        Assertions.assertEquals("CODE", directionActual.getCode());
        Assertions.assertEquals("NAME", directionActual.getName());
        Assertions.assertEquals("DESCRIPTION", directionActual.getDescription());
        Assertions.assertNotNull(directionActual.getDirection());
        Assertions.assertEquals(parentDirection.getId().toString(), directionActual.getDirection().getId().toString());
        UpdateDirectionRequestDto updateDirectionRequestDto = new UpdateDirectionRequestDto()
                .setCode("CODE2")
                .setName("NAME2")
                .setDescription("DESCRIPTION2")
                .setParentId(newParentDirection.getId());

        mockMvc.perform(
                put("/v1/direction/{id}", direction.getId())
                        .content(objectMapper.writeValueAsBytes(updateDirectionRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CODE2"))
                .andExpect(jsonPath("$.name").value("NAME2"))
                .andExpect(jsonPath("$.description").value("DESCRIPTION2"))
                .andExpect(jsonPath("$.parentDirection").isNotEmpty())
                .andExpect(jsonPath("$.parentDirection.id").value(newParentDirection.getId().toString()));

    }

    @Test
    public void executeWithException_WithDirectionThatDoesNotExist() throws Exception {
        UpdateDirectionRequestDto updateDirectionRequestDto = new UpdateDirectionRequestDto()
                .setCode("CODE2")
                .setName("NAME2")
                .setDescription("DESCRIPTION2")
                .setParentId(null);

        mockMvc.perform(
                put("/v1/direction/{id}",
                        UUID.fromString("53d2f8bc-ea56-45b4-8e28-96f14b766caf")
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateDirectionRequestDto))
        )
                .andExpect(status().is4xxClientError())
                .andExpect(
                        result -> Assertions.assertInstanceOf(
                                EntityAlreadyExistsOrDoesNotExistException.class,
                                result.getResolvedException()
                        )
                );
    }

    @Test
    public void executeWithException_WithParentDirectionThatDoesNotExist() throws Exception {

        Direction direction = new Direction()
                .setCode("CODE")
                .setName("NAME")
                .setDescription("DESCRIPTION");
        direction = directionRepository.save(direction);

        UpdateDirectionRequestDto updateDirectionRequestDto = new UpdateDirectionRequestDto()
                .setCode("CODE2")
                .setName("NAME2")
                .setDescription("DESCRIPTION2")
                .setParentId(UUID.randomUUID());

        mockMvc.perform(
                put("/v1/direction/{id}", direction.getId())
                        .content(objectMapper.writeValueAsBytes(updateDirectionRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is4xxClientError())
                .andExpect(
                        result -> Assertions.assertInstanceOf(
                                EntityAlreadyExistsOrDoesNotExistException.class,
                                result.getResolvedException()
                        )
                );

    }

}
