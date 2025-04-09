package ru.dhabits.fixchaos.planning.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dhabits.fixchaos.planning.domain.entity.ChangeThought;

import java.util.UUID;

public interface ChangeThoughtRepository extends JpaRepository<ChangeThought, UUID> {

}
