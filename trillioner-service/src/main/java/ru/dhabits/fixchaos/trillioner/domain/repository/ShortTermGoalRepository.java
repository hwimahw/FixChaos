package ru.dhabits.fixchaos.trillioner.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dhabits.fixchaos.trillioner.domain.entity.ShortTermGoal;

import java.util.UUID;

public interface ShortTermGoalRepository extends CrudRepository<ShortTermGoal, UUID> {

}
