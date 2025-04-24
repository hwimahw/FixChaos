package ru.dhabits.fixchaos.planning.usecase.goal.getsubtree;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.dhabits.fixchaos.planning.config.TestConfigHelper;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;
import ru.dhabits.fixchaos.planning.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.planning.domain.repository.GoalRepository;
import ru.dhabits.fixchaos.planning.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.planning.enumeration.GoalType;
import ru.dhabits.fixchaos.planning.service.DictionaryService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dhabits.fixchaos.planning.commons.TestData.END_DATE;
import static ru.dhabits.fixchaos.planning.commons.TestData.GOAL_NAME_1;
import static ru.dhabits.fixchaos.planning.commons.TestData.GOAL_NAME_2;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_CODE_1;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_CODE_2;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_NAME_1;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_NAME_2;
import static ru.dhabits.fixchaos.planning.commons.TestData.START_DATE;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class GetSubtreeUseCaseTest extends TestConfigHelper {


    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private MainDirectionRepository mainDirectionRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void execute_Successful() throws Exception {
        Goal goal = createAndSaveGoalWithOneChildGoal();

        mockMvc.perform(get("/v1/goal/" + goal.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(GOAL_NAME_1))
                .andExpect(jsonPath("$.startDate").value(START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(END_DATE.toString()))
                .andExpect(jsonPath("$.mainDirection").value(MAIN_DIRECTION_CODE_1))
                .andExpect(jsonPath("$.goals", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.goals[0].name").value(GOAL_NAME_2))
                .andExpect(jsonPath("$.goals[0].mainDirection").value(MAIN_DIRECTION_CODE_2))
                .andExpect(jsonPath("$.goals[0].startDate").value(START_DATE.toString()))
                .andExpect(jsonPath("$.goals[0].endDate").value(END_DATE.toString()));
    }

    private Goal createAndSaveGoalWithOneChildGoal() {
        MainDirection mainDirection1 = new MainDirection();
        mainDirection1.setCode(MAIN_DIRECTION_CODE_1);
        mainDirection1.setName(MAIN_DIRECTION_NAME_1);
        mainDirectionRepository.save(mainDirection1);

        MainDirection mainDirection2 = new MainDirection();
        mainDirection2.setCode(MAIN_DIRECTION_CODE_2);
        mainDirection2.setName(MAIN_DIRECTION_NAME_2);
        mainDirectionRepository.save(mainDirection2);

        Goal goal1 = new Goal()
                .setName(GOAL_NAME_1)
                .setMainDirection(mainDirection1)
                .setGoalType(GoalType.LONG_TERM)
                .setStartDate(START_DATE)
                .setEndDate(END_DATE);

        goalRepository.save(goal1);

        Goal goal2 = new Goal()
                .setName(GOAL_NAME_2)
                .setMainDirection(mainDirection2)
                .setGoalType(GoalType.LONG_TERM)
                .setStartDate(START_DATE)
                .setEndDate(END_DATE)
                .setGoal(goal1);
        goalRepository.save(goal2);
        return goal1;
    }
}
