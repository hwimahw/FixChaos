package ru.dhabits.fixchaos.planning.usecase.direction.getalltrees;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static ru.dhabits.fixchaos.planning.commons.TestData.CODE_3;
import static ru.dhabits.fixchaos.planning.commons.TestData.CODE_4;
import static ru.dhabits.fixchaos.planning.commons.TestData.DESCRIPTION;
import static ru.dhabits.fixchaos.planning.commons.TestData.DESCRIPTION_2;
import static ru.dhabits.fixchaos.planning.commons.TestData.DESCRIPTION_3;
import static ru.dhabits.fixchaos.planning.commons.TestData.DESCRIPTION_4;
import static ru.dhabits.fixchaos.planning.commons.TestData.NAME;
import static ru.dhabits.fixchaos.planning.commons.TestData.NAME_2;
import static ru.dhabits.fixchaos.planning.commons.TestData.NAME_3;
import static ru.dhabits.fixchaos.planning.commons.TestData.NAME_4;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class GetAllTreesOfDirectionUseCaseTest extends TestConfigHelper {

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void execute_Successful() throws Exception {
        Direction direction1 = createAndSaveDirectionWithOneChildGoal();
        Direction direction2 = createAndSaveDirectionWithOneChildGoal2();

        // Здесь для простоты учитывается, что записи будут расположены в бд в порядке их вставки, что может быть неверным
        // Для этого простого случая это истина, для других случаях при сложных запросах - ложь.
        mockMvc.perform(get("/v1/direction/alltrees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value(CODE))
                .andExpect(jsonPath("$[0].name").value(NAME))
                .andExpect(jsonPath("$[0].description").value(DESCRIPTION))
                .andExpect(jsonPath("$[0].directions", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].directions[0].code").value(CODE_2))
                .andExpect(jsonPath("$[0].directions[0].name").value(NAME_2))
                .andExpect(jsonPath("$[0].directions[0].description").value(DESCRIPTION_2))
                .andExpect(jsonPath("$[1].code").value(CODE_3))
                .andExpect(jsonPath("$[1].name").value(NAME_3))
                .andExpect(jsonPath("$[1].description").value(DESCRIPTION_3))
                .andExpect(jsonPath("$[1].directions", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[1].directions[0].code").value(CODE_4))
                .andExpect(jsonPath("$[1].directions[0].name").value(NAME_4))
                .andExpect(jsonPath("$[1].directions[0].description").value(DESCRIPTION_4))
                .andExpect(status().isOk());
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

    private Direction createAndSaveDirectionWithOneChildGoal2() {
        Direction direction1 = new Direction();
        direction1.setCode(CODE_3);
        direction1.setName(NAME_3);
        direction1.setDescription(DESCRIPTION_3);
        directionRepository.save(direction1);

        Direction direction2 = new Direction();
        direction2.setCode(CODE_4);
        direction2.setName(NAME_4);
        direction2.setDescription(DESCRIPTION_4);
        direction2.setDirection(direction1);
        directionRepository.save(direction2);

        return direction1;
    }


}
