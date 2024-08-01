package ru.dhabits.fixchaos.trillioner.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dhabits.fixchaos.trillioner.domain.entity.LongTermGoal;
import java.util.UUID;

public interface LongTermGoalRepository extends JpaRepository<LongTermGoal, UUID> {

}
