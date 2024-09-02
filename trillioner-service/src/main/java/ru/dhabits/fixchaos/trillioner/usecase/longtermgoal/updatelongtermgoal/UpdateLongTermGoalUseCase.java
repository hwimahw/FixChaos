package ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.updatelongtermgoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.trillioner.domain.entity.LongTermGoal;
import ru.dhabits.fixchaos.trillioner.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.trillioner.domain.repository.LongTermGoalRepository;
import ru.dhabits.fixchaos.trillioner.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.trillioner.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.trillioner.service.DictionaryService;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.updatelongtermgoal.request.UpdateLongTermGoalCommand;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateLongTermGoalUseCase {

    private final DictionaryService dictionaryService;
    private final MainDirectionRepository mainDirectionRepository;
    private final LongTermGoalRepository longTermGoalRepository;

    public void execute(UUID id, UpdateLongTermGoalCommand updateLongTermGoalCommand) {
        MainDirection mainDirection = dictionaryService.getEntity(
                updateLongTermGoalCommand.getMainDirection(),
                MainDirection.class,
                mainDirectionRepository
        );
        LongTermGoal updatedShortTermGoal = longTermGoalRepository.findById(id)
                .map(
                        longTermGoal -> setNewFieldsToShortTermGoal(
                                longTermGoal,
                                updateLongTermGoalCommand,
                                mainDirection)
                ).orElseThrow(EntityAlreadyExistsOrDoesNotExistException::new);

        longTermGoalRepository.save(updatedShortTermGoal);
    }

    private LongTermGoal setNewFieldsToShortTermGoal(
            LongTermGoal longTermGoal,
            UpdateLongTermGoalCommand updateLongTermGoalCommand,
            MainDirection mainDirection
    ) {
        return longTermGoal
                .setName(updateLongTermGoalCommand.getName())
                .setStartDate(updateLongTermGoalCommand.getStartDate())
                .setEndDate(updateLongTermGoalCommand.getEndDate())
                .setMainDirection(mainDirection);
    }
}
