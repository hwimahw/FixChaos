package ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.createlongtermgoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.trillioner.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.trillioner.domain.repository.LongTermGoalRepository;
import ru.dhabits.fixchaos.trillioner.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.trillioner.service.DictionaryService;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.createlongtermgoal.mapper.CreateLongTermUseCaseMapper;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.createlongtermgoal.request.LongTermGoalCommand;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.createlongtermgoal.response.LongTermGoalResultDto;

@Component
@RequiredArgsConstructor
public class CreateLongTermGoalUseCase {

    private final DictionaryService dictionaryService;
    private final MainDirectionRepository mainDirectionRepository;
    private final LongTermGoalRepository longTermGoalRepository;
    private final CreateLongTermUseCaseMapper createLongTermUseCaseMapper;

    public LongTermGoalResultDto execute(LongTermGoalCommand longTermGoalCommand) {
        MainDirection mainDirection = dictionaryService.getEntity(
                longTermGoalCommand.getMainDirection(),
                MainDirection.class,
                mainDirectionRepository
        );
        var longTermGoal = createLongTermUseCaseMapper.toLongTermGoal(longTermGoalCommand, mainDirection);
        return createLongTermUseCaseMapper.toLongTermGoalResultDto(longTermGoalRepository.save(longTermGoal));
    }
}
