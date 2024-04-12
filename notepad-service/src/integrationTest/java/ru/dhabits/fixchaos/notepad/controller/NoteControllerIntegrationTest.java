package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import com.dhabits.code.fixchaos.notepad.dto.NoteDto;
import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.dhabits.fixchaos.notepad.config.TestConfigHelper;
import ru.dhabits.fixchaos.notepad.db.model.Note;
import ru.dhabits.fixchaos.notepad.db.repository.FolderRepository;
import ru.dhabits.fixchaos.notepad.db.repository.NoteRepository;
import ru.dhabits.fixchaos.notepad.db.repository.NotebookRepository;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.notepad.service.FolderService;
import ru.dhabits.fixchaos.notepad.service.NoteService;
import ru.dhabits.fixchaos.notepad.service.NotebookService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NoteControllerIntegrationTest extends TestConfigHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FolderService folderService;

    @Autowired
    private NotebookService notebookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private NotebookRepository notebookRepository;

    @Autowired
    private NoteService noteService;

    @AfterEach
    public void setup() {
        noteRepository.deleteAll();
        notebookRepository.deleteAll();
        folderRepository.deleteAll();
    }

    @Test
    public void createNote_SuccessfulNoteCreating() throws Exception {
        //given
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("folder");
        FolderDto folderDto = folderService.createFolder(folderRequestDto);

        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setName("notebookName");
        notebookDto.setFolderId(folderDto.getId());
        NotebookDto notebookDtoResponse = notebookService.createNotebook(notebookDto);

        NoteDto noteDto = new NoteDto();
        noteDto.setName("noteName");
        noteDto.setNotebookId(notebookDtoResponse.getId());

        //when
        mockMvc.perform(post("/v1/note").with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password"))
//                        .header("Authorization", "Bearer")
                        //then
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(noteDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("noteName"));
    }


    @Test
    public void createNote_ToNotExistedNotebook_ThrowsException() throws Exception {
        //given
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("folder");
        FolderDto folderDto = folderService.createFolder(folderRequestDto);

        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setName("notebookName");
        notebookDto.setFolderId(folderDto.getId());
        NotebookDto notebookDtoResponse = notebookService.createNotebook(notebookDto);

        NoteDto noteDto = new NoteDto();
        noteDto.setName("noteName");
        noteDto.setNotebookId(UUID.randomUUID());

        //when
        mockMvc.perform(post("/v1/note")
                        //then
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(noteDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> {
                    Assertions.assertInstanceOf(EntityAlreadyExistsOrDoesNotExistException.class, result.getResolvedException());
                });
    }

    @Test
    public void updateNote_SuccessfulNoteUpdating() throws Exception {
        //given
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("folder");
        FolderDto folderDto = folderService.createFolder(folderRequestDto);

        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setName("notebookName");
        notebookDto.setFolderId(folderDto.getId());
        NotebookDto notebookDtoResponse = notebookService.createNotebook(notebookDto);

        NoteDto noteDto = new NoteDto();
        noteDto.setName("noteName");
        noteDto.setNotebookId(notebookDtoResponse.getId());
        NoteDto noteDtoResponse = noteService.createNote(noteDto);

        //when
        mockMvc.perform(put("/v1/note/{id}", noteDtoResponse.getId())
                        .header("Authorization", "Bearer")
                        .queryParam("name", "newNoteName")
                        //then
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<Note> optionalNote = noteRepository.findById(noteDtoResponse.getId());
        Assertions.assertTrue(optionalNote.isPresent());
        Assertions.assertEquals("newNoteName", optionalNote.get().getName());
    }

    @Test
    public void updateNote_ThatDoesNotExist_ThrowException() throws Exception {
        //given
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("folder");
        FolderDto folderDto = folderService.createFolder(folderRequestDto);

        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setName("notebookName");
        notebookDto.setFolderId(folderDto.getId());
        NotebookDto notebookDtoResponse = notebookService.createNotebook(notebookDto);

        NoteDto noteDto = new NoteDto();
        noteDto.setName("noteName");
        noteDto.setNotebookId(notebookDtoResponse.getId());
        noteDto = noteService.createNote(noteDto);

        //when
        mockMvc.perform(put("/v1/note/{id}", UUID.randomUUID())
                        .header("Authorization", "Bearer")
                        .queryParam("name", "newNoteName")
                        //then
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> Assertions.assertInstanceOf(EntityAlreadyExistsOrDoesNotExistException.class, result.getResolvedException()));
    }
}


