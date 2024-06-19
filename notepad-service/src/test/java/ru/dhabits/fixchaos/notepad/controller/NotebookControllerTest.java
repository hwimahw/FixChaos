package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.notepad.security.SecurityConfig;
import ru.dhabits.fixchaos.notepad.service.NotebookService;

import java.util.UUID;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotebookController.class)
@Import(SecurityConfig.class)
public class NotebookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotebookService notebookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNotebook_SuccessfulCreating() throws Exception {
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(notebookRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("notebookName"))
                .andExpect(jsonPath("id").value(uuid.toString()));
    }

    @Test
    void createNotebook_FolderOfWhichDoesNotExist_ThrowsException() throws Exception {
        NotebookDto notebookRequestDto = new NotebookDto();
        notebookRequestDto.setName("notebookName");

        {
            doThrow(new EntityAlreadyExistsOrDoesNotExistException())
                    .when(notebookService)
                    .createNotebook(notebookRequestDto);
        }

        mockMvc.perform(post("/v1/notebook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(notebookRequestDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(
                        result -> Assertions.assertInstanceOf(
                                EntityAlreadyExistsOrDoesNotExistException.class,
                                result.getResolvedException()
                        )
                );
    }

    @Test
    void updateNotebook_SuccessfulUpdating() throws Exception {
        String id = "id";
        String name = "name";

        {
            Mockito.doNothing().when(notebookService).updateNotebook(id, name);
        }

        mockMvc.perform(
                        put("/v1/notebook/{id}", id)
                                .queryParam("name", name).
                                contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void updateNotebook_ThatDoesNotExist_ThrowsException() throws Exception {
        String id = "id";
        String name = "name";

        {
            doThrow(new EntityAlreadyExistsOrDoesNotExistException())
                    .when(notebookService)
                    .updateNotebook(id, name);
        }

        mockMvc.perform(
                        put("/v1/notebook/{id}", id)
                                .queryParam("name", name)
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
}
