package ru.dhabits.fixchaos.notepad.db.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.dhabits.fixchaos.notepad.db.model.Folder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FolderRepository extends CrudRepository<Folder, UUID> {

    Boolean existsByName(String name);

    @EntityGraph(attributePaths = "notebooks")
    List<Folder> findAll();

    @EntityGraph(attributePaths = "notebooks.notes")
    Optional<Folder> findById(UUID id);
}
