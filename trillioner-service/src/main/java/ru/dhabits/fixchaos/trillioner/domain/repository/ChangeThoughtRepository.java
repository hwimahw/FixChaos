package ru.dhabits.fixchaos.trillioner.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dhabits.fixchaos.trillioner.domain.entity.ChangeThought;

import java.util.UUID;

public interface ChangeThoughtRepository extends JpaRepository<ChangeThought, UUID> {

}
