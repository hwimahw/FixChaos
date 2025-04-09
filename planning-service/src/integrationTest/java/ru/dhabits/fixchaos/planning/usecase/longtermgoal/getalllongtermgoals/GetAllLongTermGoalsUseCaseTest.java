package ru.dhabits.fixchaos.planning.usecase.longtermgoal.getalllongtermgoals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.dhabits.fixchaos.planning.config.TestConfigHelper;
import ru.dhabits.fixchaos.planning.domain.entity.LongTermGoal;
import ru.dhabits.fixchaos.planning.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.planning.domain.repository.LongTermGoalRepository;
import ru.dhabits.fixchaos.planning.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.planning.service.DictionaryService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dhabits.fixchaos.planning.commons.TestData.END_DATE;
import static ru.dhabits.fixchaos.planning.commons.TestData.GOAL_NAME;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_CODE;
import static ru.dhabits.fixchaos.planning.commons.TestData.START_DATE;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class GetAllLongTermGoalsUseCaseTest extends TestConfigHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LongTermGoalRepository longTermGoalRepository;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private MainDirectionRepository mainDirectionRepository;

    @Test
    public void execute_Successful() throws Exception {
        longTermGoalRepository.save(createLongTermGoal());
        longTermGoalRepository.save(createLongTermGoal());
        mockMvc.perform(
                        get("/v1/long-term-goal")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.numberOfElements").value(2))
                .andExpect(jsonPath("$.number").value(0));
    }

    @Test
    public void execute_SuccessfulWithOutContent() throws Exception {
        mockMvc.perform(
                        get("/v1/long-term-goal")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(0))
                .andExpect(jsonPath("$.numberOfElements").value(0))
                .andExpect(jsonPath("$.number").value(0));
    }

    private LongTermGoal createLongTermGoal() {
        MainDirection mainDirection = dictionaryService.getEntity(MAIN_DIRECTION_CODE, MainDirection.class, mainDirectionRepository);
        LongTermGoal longTermGoal = new LongTermGoal();
        longTermGoal.setName(GOAL_NAME);
        longTermGoal.setStartDate(START_DATE);
        longTermGoal.setEndDate(END_DATE);
        longTermGoal.setMainDirection(mainDirection);
        return longTermGoal;
    }
}
