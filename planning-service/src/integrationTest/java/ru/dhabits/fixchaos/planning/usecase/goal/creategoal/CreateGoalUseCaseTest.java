package ru.dhabits.fixchaos.planning.usecase.goal.creategoal;

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
import ru.dhabits.fixchaos.planning.inbound.rest.goal.creategoal.request.CreateGoalRequestDto;

import java.time.LocalDate;
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
public class CreateGoalUseCaseTest extends TestConfigHelper {

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
        Direction parentDirection = directionRepository.save(
                new Direction().setCode(CODE).setName(NAME)
        );
        CreateGoalRequestDto createGoalRequestDto = new CreateGoalRequestDto()
                .setName(NAME)
                .setGoalType(GoalType.SHORT_TERM.name())
                .setDirectionId(parentDirection.getId())
                .setStartDate(LocalDate.of(2025, 1, 1))
                .setEndDate(LocalDate.of(2026, 1, 1));

        mockMvc.perform(post("/v1/goal").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createGoalRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.goalType").value(GoalType.SHORT_TERM.name()))
                .andExpect(jsonPath("$.direction").value(CODE));
    }

    @Test
    public void execute_Successful_WithParentGoal() throws Exception {
        Direction parentDirection = directionRepository.save(
                new Direction().setCode(CODE).setName(NAME)
        );
        Goal parentGoal = new Goal()
                .setName("PARENT_NAME")
                .setStartDate(LocalDate.of(2025, 1, 1))
                .setEndDate(LocalDate.of(2026, 1, 1))
                .setDirection(parentDirection)
                .setGoalType(GoalType.LONG_TERM);

        goalRepository.save(parentGoal);

        CreateGoalRequestDto createGoalRequestDto = new CreateGoalRequestDto()
                .setName(NAME)
                .setParentId(parentGoal.getId())
                .setGoalType(GoalType.SHORT_TERM.name())
                .setDirectionId(parentDirection.getId())
                .setStartDate(LocalDate.of(2025, 1, 1))
                .setEndDate(LocalDate.of(2026, 1, 1))
                .setParentId(parentGoal.getId());

        mockMvc.perform(post("/v1/goal").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createGoalRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.goalType").value(GoalType.SHORT_TERM.name()))
                .andExpect(jsonPath("$.direction").value(CODE))
                .andExpect(jsonPath("$.parentGoal.name").value("PARENT_NAME"))
                .andExpect(jsonPath("$.parentGoal.direction").value(CODE))
                .andExpect(jsonPath("$.parentGoal.id").value(parentGoal.getId().toString()));
    }

    @Test
    public void executeWithException_WithNoDirection() throws Exception {
        CreateGoalRequestDto createGoalRequestDto = new CreateGoalRequestDto()
                .setName(NAME)
                .setGoalType(GoalType.SHORT_TERM.name())
                .setDirectionId(null)
                .setStartDate(LocalDate.of(2025, 1, 1))
                .setEndDate(LocalDate.of(2026, 1, 1));

        mockMvc.perform(post("/v1/goal").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createGoalRequestDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(
                        result -> Assertions.assertInstanceOf(
                                EntityAlreadyExistsOrDoesNotExistException.class,
                                result.getResolvedException()
                        )
                );
    }

    @Test
    public void executeWithException_WithDirectionThatDoesNotExist() throws Exception {
        CreateGoalRequestDto createGoalRequestDto = new CreateGoalRequestDto()
                .setName(NAME)
                .setGoalType(GoalType.SHORT_TERM.name())
                .setDirectionId(UUID.fromString("d36686da-20a6-4118-a0aa-d643ff3212d2"))
                .setStartDate(LocalDate.of(2025, 1, 1))
                .setEndDate(LocalDate.of(2026, 1, 1));

        mockMvc.perform(post("/v1/goal").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createGoalRequestDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(
                        result -> Assertions.assertInstanceOf(
                                EntityAlreadyExistsOrDoesNotExistException.class,
                                result.getResolvedException()
                        )
                );
    }

    @Test
    public void executeWithException_WithParentGoalThatDoesNotExist() throws Exception {
        Direction parentDirection = directionRepository.save(
                new Direction().setCode(CODE).setName(NAME)
        );
        CreateGoalRequestDto createGoalRequestDto = new CreateGoalRequestDto()
                .setName(NAME)
                .setGoalType(GoalType.SHORT_TERM.name())
                .setDirectionId(parentDirection.getId())
                .setParentId(UUID.fromString("d36686da-20a6-4118-a0aa-d643ff3212d2"))
                .setStartDate(LocalDate.of(2025, 1, 1))
                .setEndDate(LocalDate.of(2026, 1, 1));

        mockMvc.perform(post("/v1/goal").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createGoalRequestDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(
                        result -> Assertions.assertInstanceOf(
                                EntityAlreadyExistsOrDoesNotExistException.class,
                                result.getResolvedException()
                        )
                );
    }
}
