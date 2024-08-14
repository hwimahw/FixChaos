package ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.getshorttermgoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.trillioner.domain.entity.ShortTermGoal;
import ru.dhabits.fixchaos.trillioner.domain.repository.ShortTermGoalRepository;
import ru.dhabits.fixchaos.trillioner.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.trillioner.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.trillioner.service.DictionaryService;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.getshorttermgoal.response.ShortTermGoalInfo;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.getshorttermgoal.mapper.GetShortTermUseCaseMapper;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetShortTermGoalUseCase {

    private final DictionaryService dictionaryService;
    private final MainDirectionRepository mainDirectionRepository;
    private final ShortTermGoalRepository shortTermGoalRepository;
    private final GetShortTermUseCaseMapper getShortTermUseCaseMapper;

    public ShortTermGoalInfo execute(UUID id) {
        ShortTermGoal shortTermGoal = shortTermGoalRepository
                .findById(id)
                .orElseThrow(EntityAlreadyExistsOrDoesNotExistException::new);
        return getShortTermUseCaseMapper.toShortTermGoalInfo(shortTermGoal);
    }
}
