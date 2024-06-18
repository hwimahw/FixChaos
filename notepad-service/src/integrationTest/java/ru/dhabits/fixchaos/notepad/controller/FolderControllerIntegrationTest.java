package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import com.dhabits.code.fixchaos.notepad.dto.ListFolderDto;
import com.dhabits.code.fixchaos.notepad.dto.NoteDto;
import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.dhabits.fixchaos.notepad.config.TestConfigHelper;
import ru.dhabits.fixchaos.notepad.db.model.Folder;
import ru.dhabits.fixchaos.notepad.db.model.Note;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;
import ru.dhabits.fixchaos.notepad.db.repository.FolderRepository;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.notepad.service.FolderService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FolderControllerIntegrationTest extends TestConfigHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FolderService folderService;

    @Autowired
    private FolderRepository folderRepository;

    @AfterEach
    public void setup() {
        folderRepository.deleteAll();
    }

    @Test
    void createFolder_SuccessfulCreating() throws Exception {
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(folderRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("response2"))
                .andExpect(jsonPath("$.notebooks", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.notebooks[0].name").value("nameNotebook1"))
                .andExpect(jsonPath("$.notebooks[1].name").value("nameNotebook2"))
                .andExpect(status().isOk());
    }

    @Test
    void createFolder_ThatAlreadyExists_ThrowsException() throws Exception {
        UUID uuid = UUID.randomUUID();
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("response2");
        folderRequestDto.setId(uuid);

        NotebookDto notebookDto1 = new NotebookDto();
        notebookDto1.setName("nameNotebook1");

        NotebookDto notebookDto2 = new NotebookDto();
        notebookDto2.setName("nameNotebook2");

        folderRequestDto.setNotebooks(List.of(notebookDto1, notebookDto2));
        folderService.createFolder(folderRequestDto);


        mockMvc.perform(post("/v1/folder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(folderRequestDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof EntityAlreadyExistsOrDoesNotExistException));
    }

    @Test
    void updateFolder_SuccessfulUpdating() throws Exception {
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("oldName");

        FolderDto folderDtoResponse = folderService.createFolder(folderRequestDto);

        mockMvc.perform(put("/v1/folder/{id}", folderDtoResponse.getId())
                        .header("Authorization", "Bearer")
                        .queryParam("name", "newName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<Folder> optionalFolder = folderRepository.findById(folderDtoResponse.getId());

        Assertions.assertTrue(optionalFolder.isPresent());
        Assertions.assertEquals("newName", optionalFolder.get().getName());
    }

    @Test
    void updateFolder_ThatDoesNotExist_ThrowsException() throws Exception {
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("oldName");

        folderService.createFolder(folderRequestDto);

        mockMvc.perform(put("/v1/folder/{id}", UUID.fromString("102397da-f0f5-4d6f-a657-1f5ddcf98b87"))
                        .header("Authorization", "Bearer")
                        .queryParam("name", "newName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof EntityAlreadyExistsOrDoesNotExistException));
    }


    @Test
    void deleteFolder_SuccessfulDeleting() throws Exception {
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("newName");

        FolderDto folderDtoResponse = folderService.createFolder(folderRequestDto);


        mockMvc.perform(delete("/v1/folder/{id}", UUID.fromString(folderDtoResponse.getId().toString()))
                        .header("Authorization", "Bearer"))
                .andExpect(status().is2xxSuccessful());

        Optional<Folder> optionalFolder = folderRepository.findById(folderDtoResponse.getId());
        Assertions.assertTrue(optionalFolder.isEmpty());
    }

    @Test
    void deleteFolder_ThatDoesNotExist_ThrowsException() throws Exception {
        FolderDto folderRequestDto = new FolderDto();
        folderRequestDto.setName("newName");

        FolderDto folderDtoResponse = folderService.createFolder(folderRequestDto);


        mockMvc.perform(delete("/v1/folder/{id}", UUID.fromString("102397da-f0f5-4d6f-a657-1f5ddcf98b87"))
                        .header("Authorization", "Bearer"))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof EntityAlreadyExistsOrDoesNotExistException));

    }

    @Test
    void getAllFolders_SuccessfulGetting() throws Exception {
        folderRepository.save(createFolder("folder1"));
        folderRepository.save(createFolder("folder2"));


        MvcResult mvcResult = mockMvc.perform(
                        get("/v1/folder").contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        ListFolderDto listFolderDto = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                ListFolderDto.class
        );
        Assertions.assertNotNull(listFolderDto);
        Assertions.assertNotNull(listFolderDto.getFolders());

        List<FolderDto> folderDtos = listFolderDto.getFolders();
        Assertions.assertEquals(2, folderDtos.size());

        FolderDto folderDto1 = folderDtos.get(0);
        Assertions.assertEquals("folder1", folderDto1.getName());
        Assertions.assertNotNull(folderDto1.getNotebooks());
        Assertions.assertEquals(2, folderDto1.getNotebooks().size());

        NotebookDto notebookDto1 = folderDto1.getNotebooks().get(0);
        Assertions.assertNotNull(notebookDto1.getNotes());
        Assertions.assertEquals(2, notebookDto1.getNotes().size());

        FolderDto folderDto2 = folderDtos.get(1);
        Assertions.assertEquals("folder2", folderDto2.getName());
        Assertions.assertNotNull(folderDto2.getNotebooks());
        Assertions.assertEquals(2, folderDto2.getNotebooks().size());

        NotebookDto notebookDto2 = folderDto2.getNotebooks().get(0);
        Assertions.assertNotNull(notebookDto2.getNotes());
        Assertions.assertEquals(2, notebookDto2.getNotes().size());

        // или проверить можно проще
        assertThat(listFolderDto)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(createExpectedListFolderDto());
    }

    private Folder createFolder(String folderName) {
        Folder folder = new Folder().setName(folderName);
        Notebook notebook1 = new Notebook().setName("notebook1");
        notebook1.setFolder(folder);
        Note note1 = new Note().setName("note1").setNotebook(notebook1);
        Note note2 = new Note().setName("note2").setNotebook(notebook1);
        notebook1.setNotes(List.of(note1, note2));

        Notebook notebook2 = new Notebook().setName("notebook2");
        notebook2.setFolder(folder);
        Note note3 = new Note().setName("note3").setNotebook(notebook2);
        Note note4 = new Note().setName("note4").setNotebook(notebook2);
        notebook2.setNotes(List.of(note3, note4));

        folder.setNotebooks(List.of(notebook1, notebook2));

        return folder;
    }

    private ListFolderDto createExpectedListFolderDto() {
        ListFolderDto listFolderDto = new ListFolderDto();
        FolderDto folderDto1 = createFolderDto("folder1");
        FolderDto folderDto2 = createFolderDto("folder2");


        listFolderDto.setFolders(List.of(folderDto1, folderDto2));
        return listFolderDto;
    }

    private FolderDto createFolderDto(String folderDtoName) {
        FolderDto folderDto = new FolderDto();
        folderDto.setName(folderDtoName);
        NotebookDto notebookDto1 = new NotebookDto();
        notebookDto1.setName("notebook1");
        NoteDto noteDto1 = new NoteDto();
        noteDto1.setName("note1");
        NoteDto noteDto2 = new NoteDto();
        noteDto2.setName("note2");
        notebookDto1.setNotes(List.of(noteDto1, noteDto2));

        NotebookDto notebookDto2 = new NotebookDto();
        notebookDto2.setName("notebook2");
        NoteDto noteDto3 = new NoteDto();
        noteDto3.setName("note3");
        NoteDto noteDto4 = new NoteDto();
        noteDto4.setName("note4");
        notebookDto2.setNotes(List.of(noteDto3, noteDto4));

        folderDto.setNotebooks(List.of(notebookDto1, notebookDto2));
        return folderDto;
    }
}
