package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import com.dhabits.code.fixchaos.notepad.dto.ListNoteDto;
import com.dhabits.code.fixchaos.notepad.dto.NoteDto;
import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.dhabits.fixchaos.notepad.config.TestConfigHelper;
import ru.dhabits.fixchaos.notepad.db.model.Folder;
import ru.dhabits.fixchaos.notepad.db.model.Note;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        mockMvc.perform(
                        post("/v1/note")
                                .with(
                                        SecurityMockMvcRequestPostProcessors.httpBasic(
                                                "user",
                                                "password"
                                        )
                                )
//                        .header("Authorization", "Bearer")
                                //then
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(noteDto))
                )
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
        notebookService.createNotebook(notebookDto);

        NoteDto noteDto = new NoteDto();
        noteDto.setName("noteName");
        noteDto.setNotebookId(UUID.randomUUID());

        //when
        mockMvc.perform(
                        post("/v1/note")
                                //then
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(noteDto))
                )
                .andExpect(status().is4xxClientError())
                .andExpect(
                        result -> Assertions.assertInstanceOf(EntityAlreadyExistsOrDoesNotExistException.class,
                                result.getResolvedException()
                        )
                );
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
        mockMvc.perform(
                        put("/v1/note/{id}", noteDtoResponse.getId())
                                .header("Authorization", "Bearer")
                                .queryParam("name", "newNoteName")
                                //then
                                .contentType(MediaType.APPLICATION_JSON)
                )
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
        noteService.createNote(noteDto);

        //when
        mockMvc.perform(
                        put("/v1/note/{id}", UUID.randomUUID())
                                .header("Authorization", "Bearer")
                                .queryParam("name", "newNoteName")
                                //then
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

    @Test
    void deleteNote_SuccessfulDeleting() throws Exception {
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("newName");
        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setName("notebook");
        NoteDto noteDto1 = new NoteDto();
        noteDto1.setName("note1");
        NoteDto noteDto2 = new NoteDto();
        noteDto2.setName("note2");
        notebookDto.setNotes(List.of(noteDto1, noteDto2));
        folderRequestDto.setNotebooks(List.of(notebookDto));

        FolderDto folderDtoResponse = folderService.createFolder(folderRequestDto);
        Optional<Folder> optionalFolder = folderRepository.findById(folderDtoResponse.getId());
        Assertions.assertTrue(optionalFolder.isPresent());
        Folder folder = optionalFolder.get();
        Assertions.assertNotNull(folder.getNotebooks());
        Assertions.assertEquals(1, folder.getNotebooks().size());
        Notebook notebook = folder.getNotebooks().get(0);
        Assertions.assertNotNull(notebook.getNotes());
        Assertions.assertEquals(2, notebook.getNotes().size());
        Note note1 = notebook.getNotes().get(0);
        Note note2 = notebook.getNotes().get(1);

        mockMvc.perform(
                        delete("/v1/note/{id}", UUID.fromString(note1.getId().toString()))
                )
                .andExpect(status().is2xxSuccessful());

        Optional<Folder> optionalFolderCheck = folderRepository.findById(folderDtoResponse.getId());
        Assertions.assertTrue(optionalFolderCheck.isPresent());
        Folder folderCheck = optionalFolderCheck.get();
        Assertions.assertNotNull(folder.getNotebooks());
        Assertions.assertEquals(1, folderCheck.getNotebooks().size());
        Notebook notebookCheck = folderCheck.getNotebooks().get(0);
        Assertions.assertNotNull(notebookCheck.getNotes());
        Assertions.assertEquals(1, notebookCheck.getNotes().size());
        Note noteCheck = notebookCheck.getNotes().get(0);
        Assertions.assertEquals(note2.getName(), noteCheck.getName());
    }

    @Test
    void getNotesOfNotebook_SuccessfulGetting() throws Exception {
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("newName");
        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setName("notebook");
        NoteDto noteDto1 = new NoteDto();
        noteDto1.setName("note1");
        NoteDto noteDto2 = new NoteDto();
        noteDto2.setName("note2");
        notebookDto.setNotes(List.of(noteDto1, noteDto2));
        folderRequestDto.setNotebooks(List.of(notebookDto));

        FolderDto folderDtoResponse = folderService.createFolder(folderRequestDto);
        Assertions.assertNotNull(folderDtoResponse.getNotebooks());
        Assertions.assertEquals(1, folderDtoResponse.getNotebooks().size());
        NotebookDto notebookDtoResponse = folderDtoResponse.getNotebooks().get(0);

        MvcResult mvcResult = mockMvc.perform(
                        get(
                                "/v1/note/{notebookId}",
                                UUID.fromString(notebookDtoResponse.getId().toString())
                        )
                )
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        ListNoteDto listNoteDto = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                ListNoteDto.class
        );

        Assertions.assertNotNull(listNoteDto);
        Assertions.assertNotNull(listNoteDto.getNotes());
        Assertions.assertEquals(2, listNoteDto.getNotes().size());
    }

    @Test
    void getNotesOfNotebook_ThatDoesNotExist_ThrowsException() throws Exception {
        mockMvc.perform(
                        get(
                                "/v1/note/{notebookId}",
                                UUID.randomUUID().toString())
                )
                .andExpect(status().is4xxClientError())
                .andExpect(
                        result -> Assertions.assertInstanceOf(
                                EntityAlreadyExistsOrDoesNotExistException.class,
                                result.getResolvedException()
                        ));
    }

    @Test
    void getNoteById_SuccessfulGetting() throws Exception {
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("newName");
        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setName("notebook");
        NoteDto noteDto1 = new NoteDto();
        noteDto1.setName("note1");
        NoteDto noteDto2 = new NoteDto();
        noteDto2.setName("note2");
        notebookDto.setNotes(List.of(noteDto1, noteDto2));
        folderRequestDto.setNotebooks(List.of(notebookDto));

        FolderDto folderDtoResponse = folderService.createFolder(folderRequestDto);
        Assertions.assertNotNull(folderDtoResponse.getNotebooks());
        Assertions.assertEquals(1, folderDtoResponse.getNotebooks().size());
        NotebookDto notebookDtoResponse = folderDtoResponse.getNotebooks().get(0);
        Assertions.assertNotNull(notebookDtoResponse.getNotes());
        Assertions.assertEquals(2, notebookDtoResponse.getNotes().size());
        NoteDto noteDto = notebookDtoResponse.getNotes().get(0);


        MvcResult mvcResult = mockMvc.perform(
                        get(
                                "/v1/note").param("id", noteDto.getId().toString()
                        )
                )
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        NoteDto noteDtoResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                NoteDto.class
        );

        Assertions.assertNotNull(noteDtoResponse);
        Assertions.assertEquals(noteDto.getId(), noteDtoResponse.getId());
        Assertions.assertEquals(noteDto.getName(), noteDtoResponse.getName());
    }

    @Test
    void getNoteById_ThatDoesNotExist_ThrowsException() throws Exception {
        mockMvc.perform(
                        get(
                                "/v1/note").param("id", UUID.randomUUID().toString()
                        )
                )
                .andExpect(status().is4xxClientError())
                .andExpect(
                        result -> Assertions.assertInstanceOf(
                                EntityAlreadyExistsOrDoesNotExistException.class,
                                result.getResolvedException()
                        ));
    }
}


