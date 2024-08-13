package ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.deleteshorttermgoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.trillioner.domain.entity.ShortTermGoal;
import ru.dhabits.fixchaos.trillioner.domain.repository.ShortTermGoalRepository;
import ru.dhabits.fixchaos.trillioner.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.trillioner.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.trillioner.service.DictionaryService;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.createshorttermgoal.mapper.CreateShortTermUseCaseMapper;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteShortTermGoalUseCase {

    private final DictionaryService dictionaryService;
    private final MainDirectionRepository mainDirectionRepository;
    private final ShortTermGoalRepository shortTermGoalRepository;
    private final CreateShortTermUseCaseMapper createShortTermUseCaseMapper;

    public void execute(UUID id) {
        ShortTermGoal shortTermGoal = shortTermGoalRepository
                .findById(id)
                .orElseThrow(
                        EntityAlreadyExistsOrDoesNotExistException::new
                );
        shortTermGoalRepository.delete(shortTermGoal);
    }
}
