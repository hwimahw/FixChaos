package ru.dhabits.fixchaos.notepad.service;

import com.dhabits.code.fixchaos.notepad.dto.ListNoteDto;
import com.dhabits.code.fixchaos.notepad.dto.NoteDto;

public interface NoteService {
    NoteDto createNote(NoteDto noteDto);

    void updateNote(String id, String name);

    void deleteNote(String id);

    ListNoteDto getNotesOfNotebook(String notebook);

    NoteDto getNodeById(String id);
}
