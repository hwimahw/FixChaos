package ru.dhabits.fixchaos.planning.usecase.longtermgoal.createlongtermgoal;

import com.dhabits.code.fixchaos.planning.dto.LongTermGoalRequestDto;
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
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dhabits.fixchaos.planning.commons.TestData.END_DATE;
import static ru.dhabits.fixchaos.planning.commons.TestData.GOAL_NAME;
import static ru.dhabits.fixchaos.planning.commons.TestData.MAIN_DIRECTION_CODE;
import static ru.dhabits.fixchaos.planning.commons.TestData.START_DATE;
import static ru.dhabits.fixchaos.planning.commons.TestData.WRONG_MAIN_DIRECTION_CODE;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class CreateLongTermGoalUseCaseTest extends TestConfigHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void execute_Successful() throws Exception {
        mockMvc.perform(
                        post("/v1/long-term-goal")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(createLongTermGoalRequestDto()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(GOAL_NAME))
                .andExpect(jsonPath("$.startDate").value(START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(END_DATE.toString()))
                .andExpect(jsonPath("$.mainDirection").value(MAIN_DIRECTION_CODE));
    }

    @Test
    public void execute_WithNotExistingMainDirection_ThrowsException() throws Exception {
        mockMvc.perform(
                        post("/v1/long-term-goal")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(
                                        createLongTermGoalRequestDtoWithNotExistingMainDirection()
                                        )
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

    private LongTermGoalRequestDto createLongTermGoalRequestDto() {
        LongTermGoalRequestDto longTermGoalRequestDto = new LongTermGoalRequestDto();
        longTermGoalRequestDto.setName(GOAL_NAME);
        longTermGoalRequestDto.setStartDate(START_DATE);
        longTermGoalRequestDto.setEndDate(END_DATE);
        longTermGoalRequestDto.setMainDirection(MAIN_DIRECTION_CODE);

        return longTermGoalRequestDto;
    }

    private LongTermGoalRequestDto createLongTermGoalRequestDtoWithNotExistingMainDirection() {
        LongTermGoalRequestDto longTermGoalRequestDto = new LongTermGoalRequestDto();
        longTermGoalRequestDto.setName(GOAL_NAME);
        longTermGoalRequestDto.setStartDate(START_DATE);
        longTermGoalRequestDto.setEndDate(END_DATE);
        longTermGoalRequestDto.setMainDirection(WRONG_MAIN_DIRECTION_CODE);

        return longTermGoalRequestDto;
    }
}
