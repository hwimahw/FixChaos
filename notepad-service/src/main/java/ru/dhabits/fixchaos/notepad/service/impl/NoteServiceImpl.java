package ru.dhabits.fixchaos.notepad.service.impl;

import com.dhabits.code.fixchaos.notepad.dto.ListNoteDto;
import com.dhabits.code.fixchaos.notepad.dto.NoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dhabits.fixchaos.notepad.db.model.Note;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;
import ru.dhabits.fixchaos.notepad.db.repository.NoteRepository;
import ru.dhabits.fixchaos.notepad.db.repository.NotebookRepository;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.notepad.mapper.NoteMapper;
import ru.dhabits.fixchaos.notepad.service.NoteService;

import java.util.Optional;
import java.util.UUID;

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
        Note note = noteMapper.mapToNote(noteDto).setNotebook(notebookOptional.get());
        return noteMapper.mapToNoteDto(noteRepository.save(note));
    }

    @Override
    public void updateNote(String id, String name) {
        if (name == null) {
            return;
        }
        noteRepository.findById(UUID.fromString(id)).ifPresentOrElse(
                note -> {
                    note.setName(name);
                    noteRepository.save(note);
                },
                () -> {
                    throw new EntityAlreadyExistsOrDoesNotExistException();
                }
        );
    }

    @Override
    public void deleteNote(String id) {
        noteRepository.findById(UUID.fromString(id)).ifPresentOrElse(
                noteRepository::delete,
                () -> {
                    throw new EntityAlreadyExistsOrDoesNotExistException();
                }
        );
    }

    @Override
    public ListNoteDto getNotesOfNotebook(String notebookId) {
        ListNoteDto listNoteDto = new ListNoteDto();
        listNoteDto.setNotes(
                notebookRepository
                        .findById(UUID.fromString(notebookId))
                        .map(Notebook::getNotes)
                        .map(noteMapper::mapToNoteDtoList)
                        .orElseThrow(EntityAlreadyExistsOrDoesNotExistException::new)
        );
        return listNoteDto;
    }

    @Override
    public NoteDto getNodeById(String id) {
        return noteRepository
                .findById(UUID.fromString(id))
                .map(noteMapper::mapToNoteDto)
                .orElseThrow(EntityAlreadyExistsOrDoesNotExistException::new);
    }
}
