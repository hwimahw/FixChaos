package ru.dhabits.fixchaos.planning.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dhabits.fixchaos.planning.domain.entity.dictionary.Direction;


public interface DirectionRepository extends JpaRepository<Direction, String> {
}
