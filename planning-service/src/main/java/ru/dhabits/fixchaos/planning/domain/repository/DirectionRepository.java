package ru.dhabits.fixchaos.planning.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;

import java.util.UUID;


public interface DirectionRepository extends JpaRepository<Direction, UUID> {
}
