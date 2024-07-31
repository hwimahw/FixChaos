package ru.dhabits.fixchaos.trillioner.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dhabits.fixchaos.trillioner.domain.entity.LongTermGoal;
import java.util.UUID;

public interface LongTermGoalRepository extends CrudRepository<LongTermGoal, UUID> {

}
