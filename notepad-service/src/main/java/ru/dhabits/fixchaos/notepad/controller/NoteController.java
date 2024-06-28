package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.controller.NoteApi;
import com.dhabits.code.fixchaos.notepad.dto.ListNoteDto;
import com.dhabits.code.fixchaos.notepad.dto.NoteDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.dhabits.fixchaos.notepad.service.NoteService;

@Controller
@RequiredArgsConstructor
public class NoteController implements NoteApi {

    private final NoteService noteService;

    @Override
    public ResponseEntity<NoteDto> createNote(@Valid NoteDto noteDto) {
        return ResponseEntity.ok(noteService.createNote(noteDto));
    }

    @Override
    public ResponseEntity<Void> deleteNote(String id) {
        noteService.deleteNote(id);
        return ResponseEntity.status(204).build();
    }

    @Override
    public ResponseEntity<NoteDto> getNoteById(String id) {
        return ResponseEntity.ok(noteService.getNodeById(id));
    }

    @Override
    public ResponseEntity<ListNoteDto> getNotesOfNotebook(String notebookId) {
        return ResponseEntity.ok(noteService.getNotesOfNotebook(notebookId));
    }

    @Override
    public ResponseEntity<Void> updateNote(String id, @Valid String name) {
        noteService.updateNote(id, name);
        return ResponseEntity.ok().build();
    }
}
