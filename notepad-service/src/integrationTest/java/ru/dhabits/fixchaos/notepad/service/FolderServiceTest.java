package ru.dhabits.fixchaos.notepad.service;

import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
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

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FolderServiceTest extends TestConfigHelper {

    @Autowired
    private FolderService folderService;

    @Autowired
    private FolderRepository folderRepository;

    @Test
    public void testSuccessfulCreateFolder() {
        FolderDto folderDto = new FolderDto();
        folderDto.setName("folderName");

        NotebookDto notebookDto1 = new NotebookDto();
        notebookDto1.setName("notebookName1");
        NoteDto note1 = new NoteDto();
        note1.setName("note1");
        note1.setText("text1");
        NoteDto note2 = new NoteDto();
        note2.setName("note2");
        note2.setText("text2");
        notebookDto1.setNotes(List.of(note1, note2));

        NotebookDto notebookDto2 = new NotebookDto();
        notebookDto2.setName("notebookName2");
        NoteDto note3 = new NoteDto();
        note3.setName("note3");
        note3.setText("text3");
        NoteDto note4 = new NoteDto();
        note4.setName("note4");
        note4.setText("text4");
        notebookDto2.setNotes(List.of(note3, note4));
        folderDto.setNotebooks(List.of(notebookDto1, notebookDto2));

        FolderDto folderAnswer = folderService.createFolder(folderDto);

        Assertions.assertEquals("folderName", folderAnswer.getName());
        Assertions.assertEquals(2, folderAnswer.getNotebooks().size());
        Assertions.assertEquals("notebookName1", folderAnswer.getNotebooks().get(0).getName());
        Assertions.assertEquals("notebookName2", folderAnswer.getNotebooks().get(1).getName());

    }

    @Test
    public void testExceptionCreateFolder() {
        Folder folder = new Folder();
        folder.setName("folderName");
        folderRepository.save(folder);

        FolderDto folderDto = new FolderDto();
        folderDto.setName("folderName");

        Assertions.assertThrows(EntityAlreadyExistsOrDoesNotExistException.class, () -> {
            folderService.createFolder(folderDto);
        });

    }
}
