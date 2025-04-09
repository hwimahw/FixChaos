package ru.dhabits.fixchaos.planning.usecase.longtermgoal.deletelongtermgoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.domain.entity.LongTermGoal;
import ru.dhabits.fixchaos.planning.domain.repository.LongTermGoalRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteLongTermGoalUseCase {

    private final LongTermGoalRepository longTermGoalRepository;

    public void execute(UUID id) {
        LongTermGoal longTermGoal = longTermGoalRepository
                .findById(id)
                .orElseThrow(
                        EntityAlreadyExistsOrDoesNotExistException::new
                );
        longTermGoalRepository.delete(longTermGoal);
    }
}
