package ru.dhabits.fixchaos.planning.usecase.shorttermgoal.updateshorttermgoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.domain.entity.ShortTermGoal;
import ru.dhabits.fixchaos.planning.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.planning.domain.repository.ShortTermGoalRepository;
import ru.dhabits.fixchaos.planning.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.service.DictionaryService;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.updateshorttermgoal.request.UpdateShortTermGoalCommand;

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
