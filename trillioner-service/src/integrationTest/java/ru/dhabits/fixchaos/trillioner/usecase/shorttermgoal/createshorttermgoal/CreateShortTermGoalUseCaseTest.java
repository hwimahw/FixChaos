package ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.createshorttermgoal;

import com.dhabits.code.fixchaos.trillioner.dto.ShortTermGoalRequestDto;
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
import ru.dhabits.fixchaos.trillioner.error.EntityAlreadyExistsOrDoesNotExistException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dhabits.fixchaos.trillioner.commons.TestData.END_DATE;
import static ru.dhabits.fixchaos.trillioner.commons.TestData.GOAL_NAME;
import static ru.dhabits.fixchaos.trillioner.commons.TestData.MAIN_DIRECTION_CODE;
import static ru.dhabits.fixchaos.trillioner.commons.TestData.START_DATE;
import static ru.dhabits.fixchaos.trillioner.commons.TestData.WRONG_MAIN_DIRECTION_CODE;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class CreateShortTermGoalUseCaseTest extends TestConfigHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void execute_Successful() throws Exception {
        mockMvc.perform(
                        post("/v1/short-term-goal")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(createShortTermGoalRequestDto()))
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
                        post("/v1/short-term-goal")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(
                                                createShortTermGoalRequestDtoWithNotExistingMainDirection()
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

    private ShortTermGoalRequestDto createShortTermGoalRequestDto() {
        ShortTermGoalRequestDto shortTermGoalRequestDto = new ShortTermGoalRequestDto();
        shortTermGoalRequestDto.setName(GOAL_NAME);
        shortTermGoalRequestDto.setStartDate(START_DATE);
        shortTermGoalRequestDto.setEndDate(END_DATE);
        shortTermGoalRequestDto.setMainDirection(MAIN_DIRECTION_CODE);

        return shortTermGoalRequestDto;
    }

    private ShortTermGoalRequestDto createShortTermGoalRequestDtoWithNotExistingMainDirection() {
        ShortTermGoalRequestDto shortTermGoalRequestDto = new ShortTermGoalRequestDto();
        shortTermGoalRequestDto.setName(GOAL_NAME);
        shortTermGoalRequestDto.setStartDate(START_DATE);
        shortTermGoalRequestDto.setEndDate(END_DATE);
        shortTermGoalRequestDto.setMainDirection(WRONG_MAIN_DIRECTION_CODE);

        return shortTermGoalRequestDto;
    }
}
