package ru.dhabits.fixchaos.trillioner.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.dhabits.fixchaos.trillioner.domain.entity.dictionary.BaseDictionary;
import ru.dhabits.fixchaos.trillioner.error.EntityAlreadyExistsOrDoesNotExistException;

import java.util.Optional;

@Service
public class DictionaryService {
    public <T extends BaseDictionary> T getEntity(String code, Class<T> clazz, JpaRepository<T, String> jpaRepository) {
        Optional<T> entity = jpaRepository.findById(code);
        return entity.orElseThrow(() -> new EntityAlreadyExistsOrDoesNotExistException(constructMessage(code, clazz)));
    }

    private <T extends BaseDictionary> String constructMessage(String code, Class<T> clazz) {
        return "Entity " + clazz.getName() + " with code " + code + " does not exist";
    }
}
