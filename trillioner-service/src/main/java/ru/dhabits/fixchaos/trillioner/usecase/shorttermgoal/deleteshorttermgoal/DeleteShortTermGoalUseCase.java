package ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.deleteshorttermgoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.trillioner.domain.entity.ShortTermGoal;
import ru.dhabits.fixchaos.trillioner.domain.repository.ShortTermGoalRepository;
import ru.dhabits.fixchaos.trillioner.error.EntityAlreadyExistsOrDoesNotExistException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteShortTermGoalUseCase {

    private final ShortTermGoalRepository shortTermGoalRepository;

    public void execute(UUID id) {
        ShortTermGoal shortTermGoal = shortTermGoalRepository
                .findById(id)
                .orElseThrow(
                        EntityAlreadyExistsOrDoesNotExistException::new
                );
        shortTermGoalRepository.delete(shortTermGoal);
    }
}
