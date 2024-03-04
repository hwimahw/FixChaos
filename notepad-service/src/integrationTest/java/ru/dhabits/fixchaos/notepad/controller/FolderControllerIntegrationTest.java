package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.dhabits.fixchaos.notepad.config.TestConfigHelper;
import ru.dhabits.fixchaos.notepad.db.repository.FolderRepository;
import ru.dhabits.fixchaos.notepad.db.repository.NotebookRepository;
import ru.dhabits.fixchaos.notepad.mapper.NotebookMapper;
import ru.dhabits.fixchaos.notepad.service.FolderService;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FolderControllerIntegrationTest extends TestConfigHelper {

    @Autowired
    private FolderService folderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private NotebookMapper notebookMapper;

    @Autowired
    private NotebookRepository notebookRepository;

    @Test
    void createFolderTest() throws Exception {

        UUID uuid = UUID.randomUUID();
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("response2");
        folderRequestDto.setId(uuid);

        NotebookDto notebookDto1 = new NotebookDto();
        notebookDto1.setName("nameNotebook1");

        NotebookDto notebookDto2 = new NotebookDto();
        notebookDto2.setName("nameNotebook2");

        folderRequestDto.setNotebooks(List.of(notebookDto1, notebookDto2));


        mockMvc.perform(post("/v1/folder")
                .header("Authorization", "Bearer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(folderRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("response2"))
                .andExpect(jsonPath("$.notebooks", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.notebooks[0].name").value("nameNotebook1"))
                .andExpect(jsonPath("$.notebooks[1].name").value("nameNotebook2"))
                .andExpect(status().isOk());
    }

}
