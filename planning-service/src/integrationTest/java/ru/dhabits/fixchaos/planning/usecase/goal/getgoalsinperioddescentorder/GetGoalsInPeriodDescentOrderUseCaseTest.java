package ru.dhabits.fixchaos.planning.usecase.goal.getgoalsinperioddescentorder;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.dhabits.fixchaos.planning.config.TestConfigHelper;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;
import ru.dhabits.fixchaos.planning.domain.repository.DirectionRepository;
import ru.dhabits.fixchaos.planning.domain.repository.GoalRepository;
import ru.dhabits.fixchaos.planning.enumeration.GoalType;
import ru.dhabits.fixchaos.planning.service.DictionaryService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dhabits.fixchaos.planning.commons.TestData.END_DATE;
import static ru.dhabits.fixchaos.planning.commons.TestData.GOAL_NAME_1;
import static ru.dhabits.fixchaos.planning.commons.TestData.GOAL_NAME_2;
import static ru.dhabits.fixchaos.planning.commons.TestData.GOAL_NAME_3;
import static ru.dhabits.fixchaos.planning.commons.TestData.GOAL_NAME_4;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_CODE_1;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_CODE_2;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_CODE_3;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_CODE_4;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_NAME_1;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_NAME_2;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_NAME_3;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_NAME_4;
import static ru.dhabits.fixchaos.planning.commons.TestData.START_DATE;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class GetGoalsInPeriodDescentOrderUseCaseTest extends TestConfigHelper {

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private DirectionRepository mainDirectionRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void execute_Successful() throws Exception {
        Direction direction1 = new Direction();
        direction1.setCode(MAIN_DIRECTION_CODE_1);
        direction1.setName(MAIN_DIRECTION_NAME_1);
        direction1 = mainDirectionRepository.save(direction1);

        Direction direction2 = new Direction();
        direction2.setCode(MAIN_DIRECTION_CODE_2);
        direction2.setName(MAIN_DIRECTION_NAME_2);
        direction2.setDirection(direction1);
        mainDirectionRepository.save(direction2);

        Direction direction3 = new Direction();
        direction3.setCode(MAIN_DIRECTION_CODE_3);
        direction3.setName(MAIN_DIRECTION_NAME_3);
        direction3.setDirection(direction1);
        mainDirectionRepository.save(direction3);

        Direction direction4 = new Direction();
        direction4.setCode(MAIN_DIRECTION_CODE_4);
        direction4.setName(MAIN_DIRECTION_NAME_4);
        direction4.setDirection(direction2);
        mainDirectionRepository.save(direction4);

        Goal goal1 = new Goal()
                .setName(GOAL_NAME_1)
                .setDirection(direction1)
                .setGoalType(GoalType.LONG_TERM)
                .setStartDate(START_DATE)
                .setEndDate(END_DATE);

        goal1 = goalRepository.save(goal1);

        Goal goal2 = new Goal()
                .setName(GOAL_NAME_2)
                .setDirection(direction1)
                .setGoalType(GoalType.LONG_TERM)
                .setStartDate(START_DATE)
                .setEndDate(END_DATE)
                .setGoal(goal1);
        goalRepository.save(goal2);

        Goal goal3 = new Goal()
                .setName(GOAL_NAME_3)
                .setDirection(direction4)
                .setGoalType(GoalType.LONG_TERM)
                .setStartDate(START_DATE)
                .setEndDate(END_DATE);
        goal3 = goalRepository.save(goal3);

        Goal goal4 = new Goal()
                .setName(GOAL_NAME_4)
                .setDirection(direction4)
                .setGoalType(GoalType.LONG_TERM)
                .setStartDate(START_DATE)
                .setEndDate(END_DATE)
                .setGoal(goal3);
        goalRepository.save(goal4);

        mockMvc.perform(get("/v1/goal/withperiod/" + direction1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(goal1.getId().toString()))
                .andExpect(jsonPath("$[0].goals", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].goals[0].id").value(goal2.getId().toString()))
                .andExpect(jsonPath("$[1].id").value(goal3.getId().toString()))
                .andExpect(jsonPath("$[1].goals", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[1].goals[0].id").value(goal4.getId().toString()));
    }

    @Test
    public void execute_Successful_WithNoGoalsAtAll() throws Exception {
        Direction direction1 = new Direction();
        direction1.setCode(MAIN_DIRECTION_CODE_1);
        direction1.setName(MAIN_DIRECTION_NAME_1);
        direction1 = mainDirectionRepository.save(direction1);

        Direction direction2 = new Direction();
        direction2.setCode(MAIN_DIRECTION_CODE_2);
        direction2.setName(MAIN_DIRECTION_NAME_2);
        direction2.setDirection(direction1);
        mainDirectionRepository.save(direction2);

        Direction direction3 = new Direction();
        direction3.setCode(MAIN_DIRECTION_CODE_3);
        direction3.setName(MAIN_DIRECTION_NAME_3);
        direction3.setDirection(direction1);
        mainDirectionRepository.save(direction3);

        Direction direction4 = new Direction();
        direction4.setCode(MAIN_DIRECTION_CODE_4);
        direction4.setName(MAIN_DIRECTION_NAME_4);
        direction4.setDirection(direction2);
        mainDirectionRepository.save(direction4);

        mockMvc.perform(get("/v1/goal/withperiod/" + direction1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }
}
