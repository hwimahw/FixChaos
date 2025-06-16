package ru.dhabits.fixchaos.planning.usecase.direction.getsubtree;

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
import ru.dhabits.fixchaos.planning.domain.repository.DirectionRepository;
import ru.dhabits.fixchaos.planning.service.DictionaryService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dhabits.fixchaos.planning.commons.TestData.CODE;
import static ru.dhabits.fixchaos.planning.commons.TestData.CODE_2;
import static ru.dhabits.fixchaos.planning.commons.TestData.DESCRIPTION;
import static ru.dhabits.fixchaos.planning.commons.TestData.DESCRIPTION_2;
import static ru.dhabits.fixchaos.planning.commons.TestData.NAME;
import static ru.dhabits.fixchaos.planning.commons.TestData.NAME_2;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class GetSubtreeOfDirectionUseCaseTest extends TestConfigHelper {

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void execute_Successful() throws Exception {
        Direction direction = createAndSaveDirectionWithOneChildGoal();

        mockMvc.perform(get("/v1/direction/" + direction.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(CODE))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.description").value(DESCRIPTION))
                .andExpect(jsonPath("$.directions", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.directions[0].code").value(CODE_2))
                .andExpect(jsonPath("$.directions[0].name").value(NAME_2))
                .andExpect(jsonPath("$.directions[0].description").value(DESCRIPTION_2));
    }

    private Direction createAndSaveDirectionWithOneChildGoal() {
        Direction direction1 = new Direction();
        direction1.setCode(CODE);
        direction1.setName(NAME);
        direction1.setDescription(DESCRIPTION);
        directionRepository.save(direction1);

        Direction direction2 = new Direction();
        direction2.setCode(CODE_2);
        direction2.setName(NAME_2);
        direction2.setDescription(DESCRIPTION_2);
        direction2.setDirection(direction1);
        directionRepository.save(direction2);

        return direction1;
    }
}
