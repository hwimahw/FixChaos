package ru.dhabits.fixchaos.trillioner.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dhabits.fixchaos.trillioner.error.EntityAlreadyExistsOrDoesNotExistException;

import java.util.Optional;

public class DictionaryService {
    public <T> T getEntity(String code, Class<T> clazz, JpaRepository<T, String> jpaRepository) {
        Optional<T> entity = jpaRepository.findById(code);
        return entity.orElseThrow(() -> new EntityAlreadyExistsOrDoesNotExistException(constructMessage(code, clazz)));
    }

    private <T> String constructMessage(String code, Class<T> clazz) {
        return "Entity" + clazz.getName() + "with code " + code + "does not exist";
    }
}
