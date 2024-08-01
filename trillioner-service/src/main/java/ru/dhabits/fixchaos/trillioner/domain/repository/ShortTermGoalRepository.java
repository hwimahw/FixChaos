package ru.dhabits.fixchaos.trillioner.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dhabits.fixchaos.trillioner.domain.entity.ShortTermGoal;

import java.util.UUID;

public interface ShortTermGoalRepository extends JpaRepository<ShortTermGoal, UUID> {

}
