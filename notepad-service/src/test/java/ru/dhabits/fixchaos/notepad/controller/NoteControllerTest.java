package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.dto.NoteDto;
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
import ru.dhabits.fixchaos.notepad.service.NoteService;

import java.util.UUID;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
@Import(SecurityConfig.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createNote_SuccessfulCreating() throws Exception {
        NoteDto noteDto = new NoteDto();
        noteDto.setName("noteName");

        UUID noteId = UUID.randomUUID();
        noteDto.setId(noteId);

        {
            Mockito.when(noteService.createNote(noteDto)).thenReturn(noteDto);
        }

        mockMvc.perform(post("/v1/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(noteDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("noteName"))
                .andExpect(jsonPath("id").value(noteId.toString()));

    }

    @Test
    public void createNote_NotebookOfWhichDoesNotExist_ThrowsException() throws Exception {
        NoteDto noteDto = new NoteDto();
        noteDto.setName("noteName");

        UUID noteId = UUID.randomUUID();
        noteDto.setId(noteId);

        {
            Mockito.doThrow(new EntityAlreadyExistsOrDoesNotExistException()).when(noteService).createNote(noteDto);
        }

        mockMvc.perform(post("/v1/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(noteDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(
                        result -> Assertions.assertInstanceOf(
                                EntityAlreadyExistsOrDoesNotExistException.class,
                                result.getResolvedException()
                        )
                );

    }

    @Test
    public void updateNote_SuccessfulUpdating() throws Exception {
        String id = "id";
        String name = "name";

        {
            Mockito.doNothing().when(noteService).updateNote(id, name);
        }

        mockMvc.perform(put("/v1/note/{id}", id)
                        .queryParam("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateNote_ThatDoesNotExist_ThrowsException() throws Exception {
        String id = "id";
        String name = "name";

        {
            doThrow(new EntityAlreadyExistsOrDoesNotExistException()).when(noteService).updateNote(id, name);
        }

        mockMvc.perform(put("/v1/note/{id}", id)
                        .queryParam("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(
                        result -> Assertions.assertInstanceOf(
                                EntityAlreadyExistsOrDoesNotExistException.class,
                                result.getResolvedException()
                        )
                );
    }
}
