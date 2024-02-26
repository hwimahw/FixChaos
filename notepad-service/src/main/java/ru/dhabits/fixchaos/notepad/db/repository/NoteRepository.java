package ru.dhabits.fixchaos.notepad.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dhabits.fixchaos.notepad.db.model.Note;

import java.util.UUID;

public interface NoteRepository extends CrudRepository<Note, UUID> {

}
