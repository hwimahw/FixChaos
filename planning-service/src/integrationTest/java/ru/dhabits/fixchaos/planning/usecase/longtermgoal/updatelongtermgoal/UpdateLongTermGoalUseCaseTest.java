package ru.dhabits.fixchaos.planning.usecase.longtermgoal.updatelongtermgoal;

import com.dhabits.code.fixchaos.planning.dto.UpdateLongTermGoalRequestDto;
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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dhabits.fixchaos.planning.commons.TestData.END_DATE;
import static ru.dhabits.fixchaos.planning.commons.TestData.GOAL_NAME;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_CODE;
import static ru.dhabits.fixchaos.planning.commons.TestData.NEW_END_DATE;
import static ru.dhabits.fixchaos.planning.commons.TestData.NEW_GOAL_NAME;
import static ru.dhabits.fixchaos.planning.commons.TestData.NEW_MAIN_DIRECTION_CODE;
import static ru.dhabits.fixchaos.planning.commons.TestData.NEW_START_DATE;
import static ru.dhabits.fixchaos.planning.commons.TestData.START_DATE;
import static ru.dhabits.fixchaos.planning.commons.TestData.WRONG_MAIN_DIRECTION_CODE;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class UpdateLongTermGoalUseCaseTest extends TestConfigHelper {

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
        var shortTermGoal = longTermGoalRepository.save(createLongTermGoal());
        mockMvc.perform(
                        post("/v1/long-term-goal/{id}", shortTermGoal.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(createUpdateLongTermGoalRequestDto()))
                )
                .andExpect(status().isOk());

        Optional<LongTermGoal> longTermGoalOptional = longTermGoalRepository.findById(shortTermGoal.getId());
        assertNotNull(longTermGoalOptional.get());
        assertEquals(NEW_GOAL_NAME, longTermGoalOptional.get().getName());
        assertEquals(NEW_START_DATE, longTermGoalOptional.get().getStartDate());
        assertEquals(NEW_END_DATE, longTermGoalOptional.get().getEndDate());
        assertEquals(NEW_MAIN_DIRECTION_CODE, longTermGoalOptional.get().getMainDirection().getCode());
    }

    @Test
    public void execute_WithNotExistingMainDirection_ThrowsException() throws Exception {
        var shortTermGoal = longTermGoalRepository.save(createLongTermGoal());
        mockMvc.perform(
                        post("/v1/long-term-goal/{id}", shortTermGoal.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(
                                        createUpdateLongTermGoalRequestDtoWithNotExistingMainDirection())
                                )
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
    public void execute_WithNotExistingShortTermGoalThrowsException() throws Exception {
        mockMvc.perform(
                        post("/v1/long-term-goal/{id}", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(
                                        createUpdateLongTermGoalRequestDtoWithNotExistingMainDirection())
                                )
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
        MainDirection mainDirection = dictionaryService.getEntity(
                MAIN_DIRECTION_CODE,
                MainDirection.class,
                mainDirectionRepository
        );
        LongTermGoal longTermGoal = new LongTermGoal();
        longTermGoal.setName(GOAL_NAME);
        longTermGoal.setStartDate(START_DATE);
        longTermGoal.setEndDate(END_DATE);
        longTermGoal.setMainDirection(mainDirection);
        return longTermGoal;
    }

    private UpdateLongTermGoalRequestDto createUpdateLongTermGoalRequestDto() {
        var updateLongTermGoalRequestDto = new UpdateLongTermGoalRequestDto();
        updateLongTermGoalRequestDto.setName(NEW_GOAL_NAME);
        updateLongTermGoalRequestDto.setStartDate(NEW_START_DATE);
        updateLongTermGoalRequestDto.setEndDate(NEW_END_DATE);
        updateLongTermGoalRequestDto.setMainDirection(NEW_MAIN_DIRECTION_CODE);
        return updateLongTermGoalRequestDto;
    }

    private UpdateLongTermGoalRequestDto createUpdateLongTermGoalRequestDtoWithNotExistingMainDirection() {
        var updateLongTermGoalRequestDto = new UpdateLongTermGoalRequestDto();
        updateLongTermGoalRequestDto.setName(NEW_GOAL_NAME);
        updateLongTermGoalRequestDto.setStartDate(NEW_START_DATE);
        updateLongTermGoalRequestDto.setEndDate(NEW_END_DATE);
        updateLongTermGoalRequestDto.setMainDirection(WRONG_MAIN_DIRECTION_CODE);
        return updateLongTermGoalRequestDto;
    }
}
