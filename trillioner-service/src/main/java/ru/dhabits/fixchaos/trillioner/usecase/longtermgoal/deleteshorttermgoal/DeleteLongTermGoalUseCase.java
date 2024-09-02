package ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.deleteshorttermgoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.trillioner.domain.entity.LongTermGoal;
import ru.dhabits.fixchaos.trillioner.domain.repository.LongTermGoalRepository;
import ru.dhabits.fixchaos.trillioner.error.EntityAlreadyExistsOrDoesNotExistException;

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
