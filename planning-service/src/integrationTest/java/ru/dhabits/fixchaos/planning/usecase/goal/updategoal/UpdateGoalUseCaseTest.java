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
import ru.dhabits.fixchaos.planning.inbound.rest.goal.updategoal.request.UpdateGoalRequestDto;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dhabits.fixchaos.planning.commons.TestData.CODE;
import static ru.dhabits.fixchaos.planning.commons.TestData.CODE_2;
import static ru.dhabits.fixchaos.planning.commons.TestData.DESCRIPTION;
import static ru.dhabits.fixchaos.planning.commons.TestData.DESCRIPTION_2;
import static ru.dhabits.fixchaos.planning.commons.TestData.NAME;
import static ru.dhabits.fixchaos.planning.commons.TestData.NAME_2;
import static ru.dhabits.fixchaos.planning.commons.TestData.NAME_3;
import static ru.dhabits.fixchaos.planning.commons.TestData.NAME_4;

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
                .setCode(CODE)
                .setName(NAME)
                .setDescription(DESCRIPTION);
        direction = directionRepository.save(direction);

        Goal goal = new Goal().setName(NAME)
                .setGoalType(GoalType.SHORT_TERM)
                .setName(NAME)
                .setDirection(direction)
                .setStartDate(LocalDate.of(2025, 1, 1))
                .setEndDate(LocalDate.of(2026, 1, 1));
        goal = goalRepository.save(goal);

        Optional<Goal> optionalGoal = goalRepository.findById(goal.getId());
        Assertions.assertTrue(optionalGoal.isPresent());
        Goal goalActual = optionalGoal.get();
        Assertions.assertEquals(NAME, goalActual.getName());
        UpdateGoalRequestDto updateGoalRequestDto = new UpdateGoalRequestDto()
                .setName(NAME_2)
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
                .andExpect(jsonPath("$.id").value(goal.getId().toString()))
                .andExpect(jsonPath("$.name").value(NAME_2))
                .andExpect(jsonPath("$.startDate").value("2025-02-02"))
                .andExpect(jsonPath("$.endDate").value("2026-03-03"))
                .andExpect(jsonPath("$.directionId").value(direction.getId().toString()));
    }

    @Test
    public void execute_Successful_WithNewParentGoalAndNewDirection() throws Exception {
        Direction direction = new Direction()
                .setCode(CODE)
                .setName(NAME)
                .setDescription(DESCRIPTION);
        direction = directionRepository.save(direction);

        Direction newDirection = new Direction()
                .setCode(CODE_2)
                .setName(NAME_2)
                .setDescription(DESCRIPTION_2);
        newDirection = directionRepository.save(direction);

        Goal goal = new Goal()
                .setName(NAME)
                .setGoalType(GoalType.SHORT_TERM)
                .setDirection(direction)
                .setStartDate(LocalDate.of(2025, 1, 1))
                .setEndDate(LocalDate.of(2026, 1, 1));
        goal = goalRepository.save(goal);

        Goal goal2 = new Goal()
                .setName(NAME_2)
                .setGoalType(GoalType.SHORT_TERM)
                .setDirection(direction)
                .setStartDate(LocalDate.of(2027, 1, 1))
                .setEndDate(LocalDate.of(2028, 1, 1))
                .setGoal(goal);
        goal2 = goalRepository.save(goal);

        Goal newParent = new Goal()
                .setName(NAME_4)
                .setGoalType(GoalType.SHORT_TERM)
                .setDirection(direction)
                .setStartDate(LocalDate.of(2029, 1, 1))
                .setEndDate(LocalDate.of(2030, 1, 1))
                .setGoal(goal);
        newParent = goalRepository.save(goal);

        Optional<Goal> optionalGoal = goalRepository.findById(goal.getId());
        Assertions.assertTrue(optionalGoal.isPresent());
        Goal goalActual = optionalGoal.get();
        Assertions.assertEquals(NAME, goalActual.getName());
        UpdateGoalRequestDto updateGoalRequestDto = new UpdateGoalRequestDto()
                .setName(NAME_3)
                .setStartDate(LocalDate.of(2025, 2, 2))
                .setEndDate(LocalDate.of(2026, 3, 3))
                .setGoalType(GoalType.LONG_TERM)
                .setParentId(newParent.getId())
                .setDirectionId(newDirection.getId());

        mockMvc.perform(
                put("/v1/goal/{id}", goal.getId())
                        .content(objectMapper.writeValueAsBytes(updateGoalRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(goal.getId().toString()))
                .andExpect(jsonPath("$.name").value("NAME_3"))
                .andExpect(jsonPath("$.startDate").value("2025-02-02"))
                .andExpect(jsonPath("$.endDate").value("2026-03-03"))
                .andExpect(jsonPath("$.directionId").value(newDirection.getId().toString()))
                .andExpect(jsonPath("$.parentGoal.id").value(newParent.getId().toString()));
    }

    @Test
    public void execute_Successful_WithNoParentGoalAndNewDirection() throws Exception {
        Direction direction = new Direction()
                .setCode(CODE)
                .setName(NAME)
                .setDescription(DESCRIPTION);
        direction = directionRepository.save(direction);

        Direction newDirection = new Direction()
                .setCode(CODE_2)
                .setName(NAME_2)
                .setDescription(DESCRIPTION_2);
        newDirection = directionRepository.save(direction);

        Goal goal = new Goal()
                .setName(NAME)
                .setGoalType(GoalType.SHORT_TERM)
                .setDirection(direction)
                .setStartDate(LocalDate.of(2025, 1, 1))
                .setEndDate(LocalDate.of(2026, 1, 1));
        goal = goalRepository.save(goal);

        Goal goal2 = new Goal()
                .setName(NAME_2)
                .setGoalType(GoalType.SHORT_TERM)
                .setDirection(direction)
                .setStartDate(LocalDate.of(2027, 1, 1))
                .setEndDate(LocalDate.of(2028, 1, 1))
                .setGoal(goal);
        goal2 = goalRepository.save(goal);

        Optional<Goal> optionalGoal = goalRepository.findById(goal.getId());
        Assertions.assertTrue(optionalGoal.isPresent());
        Goal goalActual = optionalGoal.get();
        Assertions.assertEquals(NAME, goalActual.getName());
        UpdateGoalRequestDto updateGoalRequestDto = new UpdateGoalRequestDto()
                .setName(NAME_3)
                .setStartDate(LocalDate.of(2025, 2, 2))
                .setEndDate(LocalDate.of(2026, 3, 3))
                .setGoalType(GoalType.LONG_TERM)
                .setParentId(null)
                .setDirectionId(newDirection.getId());

        mockMvc.perform(
                put("/v1/goal/{id}", goal.getId())
                        .content(objectMapper.writeValueAsBytes(updateGoalRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(goal.getId().toString()))
                .andExpect(jsonPath("$.name").value("NAME_3"))
                .andExpect(jsonPath("$.startDate").value("2025-02-02"))
                .andExpect(jsonPath("$.endDate").value("2026-03-03"))
                .andExpect(jsonPath("$.directionId").value(newDirection.getId().toString()))
                .andExpect(jsonPath("$.parentGoal").isEmpty());
    }

    @Test
    public void executeWithException_WithWrongIdOfGoal() throws Exception {
        Direction direction = new Direction()
                .setCode(CODE)
                .setName(NAME)
                .setDescription(DESCRIPTION);
        direction = directionRepository.save(direction);

        Goal goal = new Goal().setName(NAME)
                .setGoalType(GoalType.SHORT_TERM)
                .setName(NAME)
                .setDirection(direction)
                .setStartDate(LocalDate.of(2025, 1, 1))
                .setEndDate(LocalDate.of(2026, 1, 1));
        goal = goalRepository.save(goal);

        Optional<Goal> optionalGoal = goalRepository.findById(goal.getId());
        Assertions.assertTrue(optionalGoal.isPresent());
        Goal goalActual = optionalGoal.get();
        Assertions.assertEquals(NAME, goalActual.getName());
        UpdateGoalRequestDto updateGoalRequestDto = new UpdateGoalRequestDto()
                .setName(NAME)
                .setStartDate(LocalDate.of(2025, 2, 2))
                .setEndDate(LocalDate.of(2026, 3, 3))
                .setGoalType(GoalType.LONG_TERM)
                .setDirectionId(direction.getId());
        mockMvc.perform(
                put("/v1/goal/{id}", UUID.fromString("877c6cec-e21a-4e5b-aad4-48552ac646f1"))
                        .content(objectMapper.writeValueAsBytes(updateGoalRequestDto))
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
