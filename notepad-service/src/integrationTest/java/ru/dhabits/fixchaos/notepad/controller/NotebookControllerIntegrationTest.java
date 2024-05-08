package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import com.dhabits.code.fixchaos.notepad.dto.NoteDto;
import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.dhabits.fixchaos.notepad.config.TestConfigHelper;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;
import ru.dhabits.fixchaos.notepad.db.repository.FolderRepository;
import ru.dhabits.fixchaos.notepad.db.repository.NoteRepository;
import ru.dhabits.fixchaos.notepad.db.repository.NotebookRepository;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.notepad.service.FolderService;
import ru.dhabits.fixchaos.notepad.service.NotebookService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NotebookControllerIntegrationTest extends TestConfigHelper {

    private static final UUID UUID_ID = UUID.fromString("49c3f407-9838-4b7b-b694-aa4d4faeb047");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotebookService notebookService;

    @Autowired
    private NotebookRepository notebookRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private FolderService folderService;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void setup() {
        noteRepository.deleteAll();
        notebookRepository.deleteAll();
        folderRepository.deleteAll();
    }

    @Test
    @DisplayName("Успешное создание объекта Notebook")
    public void createNotebook_ReturnsValidCreatedNotebook() throws Exception {
        //given
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("folder");
        FolderDto folderDtoResponse = folderService.createFolder(folderRequestDto);

        NoteDto note1 = new NoteDto();
        NoteDto note2 = new NoteDto();
        note1.setName("note1");
        note2.setName("note2");
        List<NoteDto> notes = List.of(note1, note2);

        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setFolderId(folderDtoResponse.getId());
        notebookDto.setName("name");
        notebookDto.setNotes(notes);

        //when
        mockMvc.perform(post("/v1/notebook")
        //then
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(notebookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.notes", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.notes[0].name").value("note1"))
                .andExpect(jsonPath("$.notes[1].name").value("note2"));
    }

    @Test
    @DisplayName("Создание объекта Notebook с привязкой к несуществующему объекту Folder. Кидает Exception")
    public void createNotebook_WithNotExistedFolder_ThrowsException() throws Exception {
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("folder");
        folderService.createFolder(folderRequestDto);

        NoteDto note1 = new NoteDto();
        NoteDto note2 = new NoteDto();
        note1.setName("note1");
        note2.setName("note2");
        List<NoteDto> notes = List.of(note1, note2);

        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setFolderId(UUID_ID);
        notebookDto.setName("name");
        notebookDto.setNotes(notes);

        mockMvc.perform(post("/v1/notebook")
                .header("Authorization", "Bearer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(notebookDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof EntityAlreadyExistsOrDoesNotExistException));
    }

    @Test
    @DisplayName("Обновление имени существующего в бд Notebook")
    public void updateNoteBook_SuccessfulUpdatingNameOfExistingNotebook() throws Exception {
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("folder");
        FolderDto folderDtoResponse = folderService.createFolder(folderRequestDto);

        NoteDto note1 = new NoteDto();
        NoteDto note2 = new NoteDto();
        note1.setName("note1");
        note2.setName("note2");
        List<NoteDto> notes = List.of(note1, note2);

        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setFolderId(folderDtoResponse.getId());
        notebookDto.setName("oldName");
        notebookDto.setNotes(notes);

        NotebookDto notebookDtoResponse = notebookService.createNotebook(notebookDto);

        mockMvc.perform(put("/v1/notebook/{id}", notebookDtoResponse.getId())
                .queryParam("name", "newName")
                .header("Authorization", "Bearer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(notebookDto)))
                .andExpect(status().isOk());

        Optional<Notebook> notebookOptional = notebookRepository.findById(notebookDtoResponse.getId());

        Assertions.assertTrue(notebookOptional.isPresent());
        Assertions.assertEquals("newName", notebookOptional.get().getName());
    }

    @Test
    @DisplayName("Обновление имени у несуществующего Notebook")
    public void updateNotebook_UpdateNameOfNotExistingNotebook_ThrowsException() throws Exception {
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("folder");
        FolderDto folderDtoResponse = folderService.createFolder(folderRequestDto);

        NoteDto note1 = new NoteDto();
        NoteDto note2 = new NoteDto();
        note1.setName("note1");
        note2.setName("note2");
        List<NoteDto> notes = List.of(note1, note2);

        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setFolderId(folderDtoResponse.getId());
        notebookDto.setName("oldName");
        notebookDto.setNotes(notes);

        notebookService.createNotebook(notebookDto);

        mockMvc.perform(put("/v1/notebook/{id}", UUID_ID)
                .queryParam("name", "newName")
                .header("Authorization", "Bearer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(notebookDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof EntityAlreadyExistsOrDoesNotExistException));
    }
}
