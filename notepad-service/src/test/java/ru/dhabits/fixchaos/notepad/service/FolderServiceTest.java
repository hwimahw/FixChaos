package ru.dhabits.fixchaos.notepad.service;

import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dhabits.fixchaos.notepad.db.model.Folder;
import ru.dhabits.fixchaos.notepad.db.model.Note;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;
import ru.dhabits.fixchaos.notepad.db.repository.FolderRepository;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.notepad.mapper.FolderMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FolderServiceTest {
    @MockBean
    private FolderRepository folderRepository;

    @Autowired
    private FolderService folderService;

    @Autowired
    private FolderMapper folderMapper;

    @Test
    public void testSuccessCreateFolder() {
        FolderDto folderDto = new FolderDto();
        folderDto.setName("request");
        folderDto.setNotebooks(new ArrayList<>());

        UUID uuid = UUID.randomUUID();
        Folder folder = new Folder();
        folder.setName("request");
        folder.setId(uuid);

        List<Notebook> notebooks = new ArrayList<>();
        Notebook notebook = new Notebook();
        Note note = new Note();
        List<Note> notes = new ArrayList<>();
        notes.add(note);
        notebook.setNotes(notes);
        notebooks.add(notebook);
        folder.setNotebooks(notebooks);

        when(folderRepository.existsByName(folderDto.getName())).thenReturn(false);
        when(folderRepository.save(any())).thenReturn(folder);

        FolderDto folderDtoResponse = folderService.createFolder(folderDto);

        Assertions.assertEquals(uuid.toString(), folderDtoResponse.getId().toString());
        Assertions.assertEquals("request", folderDtoResponse.getName());
        Assertions.assertEquals(1, folderDtoResponse.getNotebooks().size());

        NotebookDto notebookResponse = folderDtoResponse.getNotebooks().get(0);
        Assertions.assertEquals(1, notebookResponse.getNotes().size());
    }

    @Test
    public void testExceptionCreateFolder() {
        FolderDto folderDto = new FolderDto();
        folderDto.setName("request");
        folderDto.setNotebooks(new ArrayList<>());

        when(folderRepository.existsByName(folderDto.getName())).thenReturn(true);

        Assertions.assertThrowsExactly(EntityAlreadyExistsOrDoesNotExistException.class, () -> {
            folderService.createFolder(folderDto);
        });
    }
}
