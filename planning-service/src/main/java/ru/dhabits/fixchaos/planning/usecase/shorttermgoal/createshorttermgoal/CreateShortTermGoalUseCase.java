package ru.dhabits.fixchaos.planning.usecase.shorttermgoal.createshorttermgoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.planning.domain.repository.ShortTermGoalRepository;
import ru.dhabits.fixchaos.planning.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.planning.service.DictionaryService;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.createshorttermgoal.mapper.CreateShortTermUseCaseMapper;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.createshorttermgoal.request.ShortTermGoalCommand;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.createshorttermgoal.response.ShortTermGoalInfo;

@Component
@RequiredArgsConstructor
public class CreateShortTermGoalUseCase {

    private final DictionaryService dictionaryService;
    private final MainDirectionRepository mainDirectionRepository;
    private final ShortTermGoalRepository shortTermGoalRepository;
    private final CreateShortTermUseCaseMapper createShortTermUseCaseMapper;

    public ShortTermGoalInfo execute(ShortTermGoalCommand shortTermGoalCommand) {
        MainDirection mainDirection = dictionaryService.getEntity(
                shortTermGoalCommand.getMainDirection(),
                MainDirection.class,
                mainDirectionRepository
        );
        var shortTermGoal = createShortTermUseCaseMapper.toShortTermGoal(shortTermGoalCommand, mainDirection);
        return createShortTermUseCaseMapper.toShortTermGoalInfo(shortTermGoalRepository.save(shortTermGoal));
    }
}
