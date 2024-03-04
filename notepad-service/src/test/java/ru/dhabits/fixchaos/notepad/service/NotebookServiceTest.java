package ru.dhabits.fixchaos.notepad.service;

import com.dhabits.code.fixchaos.notepad.dto.NoteDto;
import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dhabits.fixchaos.notepad.db.model.Folder;
import ru.dhabits.fixchaos.notepad.db.model.Note;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;
import ru.dhabits.fixchaos.notepad.db.repository.FolderRepository;
import ru.dhabits.fixchaos.notepad.db.repository.NotebookRepository;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.notepad.mapper.NotebookMapper;
import ru.dhabits.fixchaos.notepad.mapper.NotebookMapperImpl;
import ru.dhabits.fixchaos.notepad.service.impl.NotebookServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = {NotebookServiceImpl.class, NotebookMapperImpl.class})
public class NotebookServiceTest {

    @Autowired
    private NotebookService notebookService;

    @Autowired
    private NotebookMapper notebookMapper;

    @MockBean
    private FolderRepository folderRepository;

    @MockBean
    private NotebookRepository notebookRepository;

    @Test
    public void testSuccessCreateNotebook() {
        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setName("name");
        NoteDto note1 = new NoteDto();
        note1.setName("note1");
        NoteDto note2 = new NoteDto();
        note2.setName("note2");
        notebookDto.setNotes(List.of(note1, note2));

        UUID folderId = UUID.randomUUID();
        notebookDto.setFolderId(folderId);

        Folder folder = new Folder();
        folder.setId(folderId);
        Optional<Folder> folderOptional = Optional.of(folder);

        Notebook notebookResponse = new Notebook();
        notebookResponse.setName("name");
        Note note3 = new Note();
        note1.setName("note1");
        Note note4 = new Note();
        note2.setName("note2");
        notebookResponse.setNotes(List.of(note3, note4));

        {
            Mockito.when(folderRepository.findById(folderId)).thenReturn(folderOptional);
            Mockito.when(notebookRepository.save(any())).thenReturn(notebookResponse);
        }

        NotebookDto notebookDtoResponse = notebookService.createNotebook(notebookDto);

        {
            Assertions.assertEquals("name", notebookDtoResponse.getName());
            Assertions.assertEquals(2, notebookDtoResponse.getNotes().size());
        }
    }

    @Test
    public void testExceptionCreateNotebook() {
        NotebookDto notebookDto = new NotebookDto();
        notebookDto.setName("name");
        NoteDto note1 = new NoteDto();
        note1.setName("note1");
        NoteDto note2 = new NoteDto();
        note2.setName("note2");
        notebookDto.setNotes(List.of(note1, note2));

        UUID folderId = UUID.randomUUID();
        notebookDto.setFolderId(folderId);

        Optional<Folder> folderOptional = Optional.empty();

        {
            Mockito.when(folderRepository.findById(folderId)).thenReturn(folderOptional);
        }

        {
            Assertions.assertThrowsExactly(EntityAlreadyExistsOrDoesNotExistException.class,
                    () -> {
                        notebookService.createNotebook(notebookDto);
                    });
        }
    }
}
