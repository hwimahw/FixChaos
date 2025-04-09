package ru.dhabits.fixchaos.planning.usecase.longtermgoal.getlongtermgoal;

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
import ru.dhabits.fixchaos.planning.domain.entity.LongTermGoal;
import ru.dhabits.fixchaos.planning.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.planning.domain.repository.LongTermGoalRepository;
import ru.dhabits.fixchaos.planning.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.service.DictionaryService;

import java.util.UUID;

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
public class GetLongTermGoalUseCaseTest extends TestConfigHelper {

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
        var longTermGoal = longTermGoalRepository.save(createLongTermGoal());
        mockMvc.perform(
                        get("/v1/long-term-goal/{id}", longTermGoal.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(GOAL_NAME))
                .andExpect(jsonPath("$.startDate").value(START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(END_DATE.toString()))
                .andExpect(jsonPath("$.mainDirection").value(MAIN_DIRECTION_CODE));
    }

    @Test
    public void execute_WithNotExistingShortTermGoalThrowsException() throws Exception {
        mockMvc.perform(
                        get("/v1/long-term-goal/{id}", UUID.randomUUID().toString())
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
