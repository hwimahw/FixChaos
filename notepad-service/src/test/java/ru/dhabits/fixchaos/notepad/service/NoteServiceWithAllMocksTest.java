package ru.dhabits.fixchaos.notepad.service;

import com.dhabits.code.fixchaos.notepad.dto.NoteDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@ExtendWith(MockitoExtension.class)
public class NoteServiceWithAllMocksTest {

    @Mock
    private NoteMapper noteMapper;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private NotebookRepository notebookRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    @Test
    public void testSuccessfulCreateNote() {
        NoteDto noteDto = new NoteDto();
        noteDto.setName("noteDtoName");
        UUID notebookId = UUID.randomUUID();
        noteDto.setNotebookId(notebookId);

        Notebook notebook = new Notebook();
        Optional<Notebook> optionalNotebook = Optional.of(notebook);

        Note note = new Note();
        note.setName("noteDtoName");
        note.setText("text");
        UUID noteId = UUID.randomUUID();
        note.setId(noteId);
        note.setNotebook(notebook);


        {
            Mockito.when(notebookRepository.findById(notebookId)).thenReturn(optionalNotebook);
            Mockito.when(noteRepository.save(any())).thenReturn(note);
            Mockito.when(noteMapper.mapToNote(noteDto)).thenReturn(note);
            Mockito.when(noteMapper.mapToNoteDto(note)).thenReturn(noteDto);
        }

        NoteDto noteDtoResponse = noteService.createNote(noteDto);

        {
            Assertions.assertEquals("noteDtoName", noteDtoResponse.getName());
            Assertions.assertEquals(noteDto.getId(), noteDtoResponse.getId());
        }
    }

    @Test
    public void testExceptionCreateNote() {
        NoteDto noteDto = new NoteDto();
        noteDto.setName("noteDtoName");
        UUID notebookId = UUID.randomUUID();
        noteDto.setNotebookId(notebookId);

        Optional<Notebook> optionalNotebook = Optional.empty();

        {
            Mockito.when(notebookRepository.findById(notebookId)).thenReturn(optionalNotebook);
        }

        {
            Assertions.assertThrowsExactly(EntityAlreadyExistsOrDoesNotExistException.class,
                    () -> {
                        noteService.createNote(noteDto);
                    });
        }
    }
}
