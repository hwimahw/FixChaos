package ru.dhabits.fixchaos.notepad.service;

import com.dhabits.code.fixchaos.notepad.dto.NoteDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.dhabits.fixchaos.notepad.db.model.Note;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;
import ru.dhabits.fixchaos.notepad.db.repository.NoteRepository;
import ru.dhabits.fixchaos.notepad.db.repository.NotebookRepository;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.notepad.mapper.NoteMapper;
import ru.dhabits.fixchaos.notepad.mapper.NoteMapperImpl;
import ru.dhabits.fixchaos.notepad.service.impl.NoteServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = {NoteServiceImpl.class, NoteMapperImpl.class})
public class NoteServiceTest {

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteMapper noteMapper;

    @MockitoBean
    private NoteRepository noteRepository;

    @MockitoBean
    private NotebookRepository notebookRepository;

    @Test
    public void createNote_SuccessfulCreating() {
        NoteDto noteDto = new NoteDto();
        noteDto.setName("noteDtoName");
        UUID notebookId = UUID.randomUUID();
        noteDto.setNotebookId(notebookId);

        Notebook notebook = new Notebook();
        Optional<Notebook> optionalNotebook = Optional.of(notebook);

        Note note = new Note();
        note.setName("noteName");
        note.setText("text");
        UUID noteId = UUID.randomUUID();
        note.setId(noteId);
        note.setNotebook(notebook);

        {
            Mockito.when(notebookRepository.findById(notebookId)).thenReturn(optionalNotebook);
            Mockito.when(noteRepository.save(any())).thenReturn(note);
        }

        NoteDto noteDtoResponse = noteService.createNote(noteDto);

        {
            Assertions.assertEquals("noteName", noteDtoResponse.getName());
            Assertions.assertEquals(noteId, noteDtoResponse.getId());
        }
    }

    @Test
    public void createNote_NotebookOfWhichDoesNotExist_ThrowsException() {
        NoteDto noteDto = new NoteDto();
        noteDto.setName("noteDtoName");
        UUID notebookId = UUID.randomUUID();
        noteDto.setNotebookId(notebookId);

        Optional<Notebook> optionalNotebook = Optional.empty();

        {
            Mockito.when(notebookRepository.findById(notebookId)).thenReturn(optionalNotebook);
        }

        {
            Assertions.assertThrowsExactly(
                    EntityAlreadyExistsOrDoesNotExistException.class,
                    () -> noteService.createNote(noteDto)
            );
        }
    }
}
