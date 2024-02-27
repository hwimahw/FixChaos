package ru.dhabits.fixchaos.notepad.service.impl;

import com.dhabits.code.fixchaos.notepad.dto.NoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dhabits.fixchaos.notepad.db.model.Folder;
import ru.dhabits.fixchaos.notepad.db.model.Note;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;
import ru.dhabits.fixchaos.notepad.db.repository.NoteRepository;
import ru.dhabits.fixchaos.notepad.db.repository.NotebookRepository;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.notepad.mapper.NoteMapper;
import ru.dhabits.fixchaos.notepad.service.NoteService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteMapper noteMapper;
    private final NoteRepository noteRepository;
    private final NotebookRepository notebookRepository;

    @Override
    public NoteDto createNote(NoteDto noteDto) {
        Optional<Notebook> notebookOptional = notebookRepository.findById(noteDto.getNotebookId());
        if (notebookOptional.isEmpty()) {
            throw new EntityAlreadyExistsOrDoesNotExistException("Notebook with id " + noteDto.getNotebookId() + " does not exist");
        }
        Notebook notebook = notebookOptional.get();
        Note note = noteMapper.mapToNote(noteDto);
        note.setNotebook(notebook);
        return noteMapper.mapToNoteDto(noteRepository.save(note));
    }
}
