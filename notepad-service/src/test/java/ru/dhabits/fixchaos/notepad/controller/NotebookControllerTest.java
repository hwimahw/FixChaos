package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.dhabits.fixchaos.notepad.service.NotebookService;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotebookController.class)
public class NotebookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotebookService notebookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNotebookTest() throws Exception {
        NotebookDto notebookRequestDto = new NotebookDto();
        notebookRequestDto.setName("notebookName");

        NotebookDto notebookResponseDto = new NotebookDto();
        UUID uuid = UUID.randomUUID();
        notebookResponseDto.setName("notebookName");
        notebookResponseDto.setId(uuid);

        {
            Mockito.when(notebookService.createNotebook(notebookRequestDto)).thenReturn(notebookResponseDto);
        }

        mockMvc.perform(post("/v1/notebook")
                .header("Authorization", "Bearer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(notebookRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("notebookName"))
                .andExpect(jsonPath("id").value(uuid.toString()));
    }
}
