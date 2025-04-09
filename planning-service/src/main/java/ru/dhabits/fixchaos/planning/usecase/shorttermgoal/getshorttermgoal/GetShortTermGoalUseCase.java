package ru.dhabits.fixchaos.planning.usecase.shorttermgoal.getshorttermgoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.domain.entity.ShortTermGoal;
import ru.dhabits.fixchaos.planning.domain.repository.ShortTermGoalRepository;
import ru.dhabits.fixchaos.planning.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.service.DictionaryService;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.getshorttermgoal.response.ShortTermGoalInfo;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.getshorttermgoal.mapper.GetShortTermUseCaseMapper;

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
