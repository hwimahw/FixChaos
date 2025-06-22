package ru.dhabits.fixchaos.planning.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface DirectionRepository extends JpaRepository<Direction, UUID> {

    @EntityGraph(attributePaths = {"directions"})
    Optional<Direction> findById(UUID id);

    List<Direction> findByDirectionIsNull();
}
