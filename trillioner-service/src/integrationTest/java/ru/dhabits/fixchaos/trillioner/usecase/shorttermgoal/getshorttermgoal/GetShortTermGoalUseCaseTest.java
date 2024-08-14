package ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.getshorttermgoal;

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
import ru.dhabits.fixchaos.trillioner.config.TestConfigHelper;
import ru.dhabits.fixchaos.trillioner.domain.entity.ShortTermGoal;
import ru.dhabits.fixchaos.trillioner.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.trillioner.domain.repository.ShortTermGoalRepository;
import ru.dhabits.fixchaos.trillioner.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.trillioner.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.trillioner.service.DictionaryService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dhabits.fixchaos.trillioner.commons.TestData.END_DATE;
import static ru.dhabits.fixchaos.trillioner.commons.TestData.GOAL_NAME;
import static ru.dhabits.fixchaos.trillioner.commons.TestData.MAIN_DIRECTION_CODE;
import static ru.dhabits.fixchaos.trillioner.commons.TestData.START_DATE;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class GetShortTermGoalUseCaseTest extends TestConfigHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ShortTermGoalRepository shortTermGoalRepository;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private MainDirectionRepository mainDirectionRepository;

    @Test
    public void execute_Successful() throws Exception {
        var shortTermGoal = shortTermGoalRepository.save(createShortTermGoal());
        mockMvc.perform(
                        get("/v1/short-term-goal/{id}", shortTermGoal.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        Optional<ShortTermGoal> shortTermGoalOptional = shortTermGoalRepository.findById(shortTermGoal.getId());
        assertTrue(shortTermGoalOptional.isPresent());
        assertEquals(GOAL_NAME, shortTermGoalOptional.get().getName());
        assertEquals(START_DATE, shortTermGoalOptional.get().getStartDate());
        assertEquals(END_DATE, shortTermGoalOptional.get().getEndDate());
        assertEquals(MAIN_DIRECTION_CODE, shortTermGoalOptional.get().getMainDirection().getCode());
    }

    @Test
    public void execute_WithNotExistingShortTermGoalThrowsException() throws Exception {
        mockMvc.perform(
                        get("/v1/short-term-goal/{id}", UUID.randomUUID().toString())
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

    private ShortTermGoal createShortTermGoal() {
        MainDirection mainDirection = dictionaryService.getEntity(MAIN_DIRECTION_CODE, MainDirection.class, mainDirectionRepository);
        ShortTermGoal shortTermGoal = new ShortTermGoal();
        shortTermGoal.setName(GOAL_NAME);
        shortTermGoal.setStartDate(START_DATE);
        shortTermGoal.setEndDate(END_DATE);
        shortTermGoal.setMainDirection(mainDirection);
        return shortTermGoal;
    }
}
