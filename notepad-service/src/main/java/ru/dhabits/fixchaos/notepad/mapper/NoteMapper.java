package ru.dhabits.fixchaos.notepad.mapper;

import com.dhabits.code.fixchaos.notepad.dto.NoteDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.notepad.db.model.Note;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    Note mapToNote(NoteDto noteDto);

    NoteDto mapToNoteDto(Note note);
}
