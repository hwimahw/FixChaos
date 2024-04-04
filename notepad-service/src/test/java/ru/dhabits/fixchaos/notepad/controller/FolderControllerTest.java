package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.notepad.service.FolderService;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FolderController.class)
public class FolderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FolderService folderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createFolderTest() throws Exception {
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("request");

        UUID uuid = UUID.randomUUID();
        FolderDto folderAnswerDto = new FolderDto();
        folderAnswerDto.setName("response");
        folderAnswerDto.setId(uuid);

        {
            when(folderService.createFolder(folderRequestDto)).thenReturn(folderAnswerDto);
        }
        mockMvc.perform(post("/v1/folder")
                .header("Authorization", "Bearer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(folderRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("response"))
                .andExpect(jsonPath("id").value(uuid.toString()));
    }

    @Test
    public void UpdateFolderTest() throws Exception {
        String id = "10";
        String name = "newName";

        {
            doNothing().when(folderService).updateFolder(id, name);
        }

        mockMvc.perform(put("/v1/folder/{id}", id)
                .queryParam("name", name))
                .andExpect(status().isOk());
    }

    @Test
    public void UpdateFolderTestException() throws Exception {
        String id = "10";
        String name = "newName";

        {
            doThrow(new EntityAlreadyExistsOrDoesNotExistException()).when(folderService).updateFolder(id, name);
        }

        mockMvc.perform(put("/v1/folder/{id}", id)
                .queryParam("name", name))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof RuntimeException));
    }
}
