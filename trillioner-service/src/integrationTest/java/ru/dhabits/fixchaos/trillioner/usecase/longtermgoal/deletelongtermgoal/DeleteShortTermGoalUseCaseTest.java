package ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.deletelongtermgoal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.dhabits.fixchaos.trillioner.config.TestConfigHelper;
import ru.dhabits.fixchaos.trillioner.domain.entity.LongTermGoal;
import ru.dhabits.fixchaos.trillioner.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.trillioner.domain.repository.LongTermGoalRepository;
import ru.dhabits.fixchaos.trillioner.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.trillioner.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.trillioner.service.DictionaryService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dhabits.fixchaos.trillioner.commons.TestData.END_DATE;
import static ru.dhabits.fixchaos.trillioner.commons.TestData.GOAL_NAME;
import static ru.dhabits.fixchaos.trillioner.commons.TestData.MAIN_DIRECTION_CODE;
import static ru.dhabits.fixchaos.trillioner.commons.TestData.START_DATE;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class DeleteShortTermGoalUseCaseTest extends TestConfigHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LongTermGoalRepository longTermGoalRepository;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private MainDirectionRepository mainDirectionRepository;

    @Test
    public void execute_Successful() throws Exception {
        var shortTermGoal = longTermGoalRepository.save(createLongTermGoal());
        mockMvc.perform(
                        delete("/v1/long-term-goal/{id}", shortTermGoal.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        Optional<LongTermGoal> longTermGoalOptional = longTermGoalRepository.findById(shortTermGoal.getId());
        assertTrue(longTermGoalOptional.isEmpty());
    }

    @Test
    public void execute_WithNotExistingShortTermGoal_ThrowsException() throws Exception {
        mockMvc.perform(
                        delete("/v1/long-term-goal/{id}", UUID.randomUUID())
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