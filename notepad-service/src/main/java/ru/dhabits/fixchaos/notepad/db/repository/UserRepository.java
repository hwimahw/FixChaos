package ru.dhabits.fixchaos.notepad.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dhabits.fixchaos.notepad.db.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByUserName(String userName);
}
