package ru.dhabits.fixchaos.trillioner.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dhabits.fixchaos.trillioner.domain.entity.ChangeThought;

import java.util.UUID;

public interface ChangeThoughtRepository extends CrudRepository<ChangeThought, UUID> {

}
