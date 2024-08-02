package ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.createshortterm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.trillioner.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.trillioner.domain.repository.ShortTermGoalRepository;
import ru.dhabits.fixchaos.trillioner.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.trillioner.service.DictionaryService;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.createshortterm.mapper.CreateShortTermUseCaseMapper;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.createshortterm.request.ShortTermGoalCommand;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.createshortterm.response.ShortTermGoalInfo;

@Component
@RequiredArgsConstructor
public class CreateShortTermUseCase {

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
