package ru.dhabits.fixchaos.planning.usecase.goal.deletegoal;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dhabits.fixchaos.planning.commons.TestData.END_DATE;
import static ru.dhabits.fixchaos.planning.commons.TestData.GOAL_NAME_1;
import static ru.dhabits.fixchaos.planning.commons.TestData.GOAL_NAME_2;
import static ru.dhabits.fixchaos.planning.commons.TestData.START_DATE;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class DeleteGoalUseCaseTest extends TestConfigHelper {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private DirectionRepository directionRepository;

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

        Goal parentGoal = new Goal()
                .setName(GOAL_NAME_1)
                .setGoalType(GoalType.LONG_TERM)
                .setStartDate(START_DATE)
                .setEndDate(END_DATE)
                .setDirection(direction);
        parentGoal = goalRepository.save(parentGoal);

        Goal goal = new Goal()
                .setName(GOAL_NAME_2)
                .setGoalType(GoalType.LONG_TERM)
                .setStartDate(START_DATE)
                .setEndDate(END_DATE)
                .setGoal(parentGoal)
                .setDirection(direction);
        goal = goalRepository.save(goal);

        Assertions.assertFalse(directionRepository.findById(direction.getId()).isEmpty());

        mockMvc.perform(
                delete("/v1/goal/{id}", parentGoal.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is(204));

        Assertions.assertTrue(goalRepository.findById(parentGoal.getId()).isEmpty());
        Assertions.assertTrue(goalRepository.findById(goal.getId()).isEmpty());
        Assertions.assertFalse(directionRepository.findById(direction.getId()).isEmpty());
    }
}
