package ru.dhabits.fixchaos.notepad.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;

import java.util.UUID;

public interface NotebookRepository extends CrudRepository<Notebook, UUID> {

}
