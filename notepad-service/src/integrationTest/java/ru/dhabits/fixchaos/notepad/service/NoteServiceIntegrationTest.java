package ru.dhabits.fixchaos.notepad.service;

import com.dhabits.code.fixchaos.notepad.dto.NoteDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.dhabits.fixchaos.notepad.config.TestConfigHelper;
import ru.dhabits.fixchaos.notepad.db.model.Folder;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;
import ru.dhabits.fixchaos.notepad.db.repository.FolderRepository;
import ru.dhabits.fixchaos.notepad.db.repository.NotebookRepository;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;

import java.util.UUID;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class NoteServiceIntegrationTest extends TestConfigHelper {

    @Autowired
    private NoteService noteService;

    @Autowired
    private NotebookRepository notebookRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Test
    public void createNote_SuccessfulCreating() {
        Folder folder = new Folder();
        folder.setName("folderName");
        folder = folderRepository.save(folder);

        Notebook notebook = new Notebook();
        notebook.setName("notebookName");
        notebook.setFolder(folder);
        notebook = notebookRepository.save(notebook);

        NoteDto noteDto = new NoteDto();
        noteDto.setName("noteName");
        noteDto.setText("text");
        noteDto.setNotebookId(notebook.getId());

        NoteDto noteDtoResponse = noteService.createNote(noteDto);

        Assertions.assertEquals("noteName", noteDtoResponse.getName());
        Assertions.assertEquals("text", noteDtoResponse.getText());
    }

    @Test
    public void createNote_NotebookOfWhichDoesNotExist_ThrowsException() {
        Folder folder = new Folder();
        folder.setName("folderName");
        folder = folderRepository.save(folder);

        Notebook notebook = new Notebook();
        notebook.setName("notebookName");
        notebook.setFolder(folder);
        notebook = notebookRepository.save(notebook);

        NoteDto noteDto = new NoteDto();
        noteDto.setName("noteName");
        noteDto.setText("text");
        noteDto.setNotebookId(UUID.randomUUID());

        Assertions.assertThrows(EntityAlreadyExistsOrDoesNotExistException.class, () -> {
            noteService.createNote(noteDto);
        });
    }

}
