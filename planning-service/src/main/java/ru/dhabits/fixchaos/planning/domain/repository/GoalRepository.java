package ru.dhabits.fixchaos.planning.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;

import java.util.UUID;

public interface GoalRepository extends JpaRepository<Goal, UUID> {
}
