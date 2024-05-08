package ru.dhabits.fixchaos.notepad.service;

import com.dhabits.code.fixchaos.notepad.dto.NoteDto;
import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dhabits.fixchaos.notepad.config.TestConfigHelper;
import ru.dhabits.fixchaos.notepad.db.model.Folder;
import ru.dhabits.fixchaos.notepad.db.repository.FolderRepository;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NotebookServiceTest extends TestConfigHelper {

    @Autowired
    private NotebookService notebookService;

    @Autowired
    private FolderRepository folderRepository;

    @Test
    public void createNotebook_SuccessfulCreating() {
        Folder folder = new Folder();
        folder.setName("folderName");
        folder = folderRepository.save(folder);

        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setFolderId(folder.getId());
        notebookDto.setName("notebookName");
        NoteDto note1 = new NoteDto();
        note1.setName("note1");
        note1.setText("text1");
        NoteDto note2 = new NoteDto();
        note2.setName("note2");
        note2.setText("text2");
        notebookDto.setNotes(List.of(note1, note2));

        NotebookDto notebookAnswer = notebookService.createNotebook(notebookDto);

        Assertions.assertEquals("notebookName", notebookAnswer.getName());
        Assertions.assertEquals(2, notebookAnswer.getNotes().size());
        Assertions.assertEquals("note1", notebookAnswer.getNotes().get(0).getName());
        Assertions.assertEquals("note2", notebookAnswer.getNotes().get(1).getName());
    }

    @Test
    public void createNotebook_FolderOfWhichDoesNotExist_ThrowsException() {
        Folder folder = new Folder();
        folder.setName("folderName");
        folderRepository.save(folder);

        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setFolderId(UUID.randomUUID());
        notebookDto.setName("notebookName");
        NoteDto note1 = new NoteDto();
        note1.setName("note1");
        note1.setText("text1");
        NoteDto note2 = new NoteDto();
        note2.setName("note2");
        note2.setText("text2");
        notebookDto.setNotes(List.of(note1, note2));

        Assertions.assertThrows(EntityAlreadyExistsOrDoesNotExistException.class, () -> {
            notebookService.createNotebook(notebookDto);
        });
    }
}
