package ru.dhabits.fixchaos.planning.usecase.goal.updategoal;

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
import ru.dhabits.fixchaos.planning.domain.entity.Goal;
import ru.dhabits.fixchaos.planning.domain.repository.DirectionRepository;
import ru.dhabits.fixchaos.planning.domain.repository.GoalRepository;
import ru.dhabits.fixchaos.planning.enumeration.GoalType;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.inbound.rest.direction.updatedirection.request.UpdateDirectionRequestDto;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.updategoal.request.UpdateGoalRequestDto;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dhabits.fixchaos.planning.commons.TestData.NAME;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class UpdateGoalUseCaseTest extends TestConfigHelper {

    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void execute_Successful_WithNoParentGoal() throws Exception {
        Direction direction = new Direction()
                .setCode("CODE")
                .setName("NAME")
                .setDescription("DESCRIPTION");
        direction = directionRepository.save(direction);

        Goal goal = new Goal().setName(NAME)
                .setGoalType(GoalType.SHORT_TERM)
                .setName("NAME")
                .setDirection(direction)
                .setStartDate(LocalDate.of(2025, 1, 1))
                .setEndDate(LocalDate.of(2026, 1, 1));
        goal = goalRepository.save(goal);

        Optional<Goal> optionalGoal = goalRepository.findById(goal.getId());
        Assertions.assertTrue(optionalGoal.isPresent());
        Goal goalActual = optionalGoal.get();
        Assertions.assertEquals("NAME", goalActual.getName());
        UpdateGoalRequestDto updateGoalRequestDto = new UpdateGoalRequestDto()
                .setName("NAME2")
                .setStartDate(LocalDate.of(2025, 2, 2))
                .setEndDate(LocalDate.of(2026, 3, 3))
                .setGoalType(GoalType.LONG_TERM)
                .setDirectionId(direction.getId());

        mockMvc.perform(
                put("/v1/goal/{id}", goal.getId())
                        .content(objectMapper.writeValueAsBytes(updateGoalRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NAME2"))
                .andExpect(jsonPath("$.startDate").value("2025-02-02"))
                .andExpect(jsonPath("$.endDate").value("2026-03-03"));
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
