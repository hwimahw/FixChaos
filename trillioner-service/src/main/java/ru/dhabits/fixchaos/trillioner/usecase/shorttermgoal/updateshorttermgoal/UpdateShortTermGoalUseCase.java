package ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.updateshorttermgoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.trillioner.domain.entity.ShortTermGoal;
import ru.dhabits.fixchaos.trillioner.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.trillioner.domain.repository.ShortTermGoalRepository;
import ru.dhabits.fixchaos.trillioner.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.trillioner.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.trillioner.service.DictionaryService;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.updateshorttermgoal.request.UpdateShortTermGoalCommand;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateShortTermGoalUseCase {

    private final DictionaryService dictionaryService;
    private final MainDirectionRepository mainDirectionRepository;
    private final ShortTermGoalRepository shortTermGoalRepository;

    public void execute(UUID id, UpdateShortTermGoalCommand updateShortTermGoalCommand) {
        MainDirection mainDirection = dictionaryService.getEntity(
                updateShortTermGoalCommand.getMainDirection(),
                MainDirection.class,
                mainDirectionRepository
        );
        ShortTermGoal updatedShortTermGoal = shortTermGoalRepository.findById(id)
                .map(
                        shortTermGoal -> setNewFieldsToShortTermGoal(
                                shortTermGoal,
                                updateShortTermGoalCommand,
                                mainDirection)
                ).orElseThrow(EntityAlreadyExistsOrDoesNotExistException::new);

        shortTermGoalRepository.save(updatedShortTermGoal);
    }

    private ShortTermGoal setNewFieldsToShortTermGoal(
            ShortTermGoal shortTermGoal,
            UpdateShortTermGoalCommand updateShortTermGoalCommand,
            MainDirection mainDirection
    ) {
        return shortTermGoal
                .setName(updateShortTermGoalCommand.getName())
                .setStartDate(updateShortTermGoalCommand.getStartDate())
                .setEndDate(updateShortTermGoalCommand.getEndDate())
                .setMainDirection(mainDirection);
    }
}
