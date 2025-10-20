package ru.dhabits.fixchaos.planning.usecase.goal.updategoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;
import ru.dhabits.fixchaos.planning.domain.repository.DirectionRepository;
import ru.dhabits.fixchaos.planning.domain.repository.GoalRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.usecase.goal.updategoal.command.UpdateGoalCommand;
import ru.dhabits.fixchaos.planning.usecase.goal.updategoal.validator.UpdateGoalValidatorService;

@Component
@RequiredArgsConstructor
public class UpdateGoalUseCase {

    private final GoalRepository goalRepository;
    private final DirectionRepository directionRepository;
    private final UpdateGoalValidatorService updateGoalValidatorService;

    @Transactional
    public Goal execute(UpdateGoalCommand updateGoalCommand) {
        updateGoalValidatorService.validateRequest(updateGoalCommand);

        Goal goal = goalRepository.findById(updateGoalCommand.getId()).orElseThrow(
                () -> new EntityAlreadyExistsOrDoesNotExistException(
                        String.format("Цель с id %s не найдена", updateGoalCommand.getId())
                )
        );
        goal.setName(updateGoalCommand.getName());
        goal.setStartDate(updateGoalCommand.getStartDate());
        goal.setEndDate(updateGoalCommand.getEndDate());
        goal.setGoalType(updateGoalCommand.getGoalType());
        if(updateGoalCommand.getDirectionId() != null) {
                Direction direction = directionRepository.findById(updateGoalCommand.getDirectionId()).orElseThrow(
                        () -> new EntityAlreadyExistsOrDoesNotExistException(
                                String.format(
                                        "Направление с id %s не найдено",
                                        updateGoalCommand.getDirectionId()
                                )
                        )
                );
                goal.setDirection(direction);
        }
        if (updateGoalCommand.getParentId() != null) {
            Goal parentGoal = goalRepository.findById(updateGoalCommand.getParentId()).orElseThrow(
                    () -> new EntityAlreadyExistsOrDoesNotExistException(
                            String.format(
                                    "Родительская цель с id %s не найдена",
                                    updateGoalCommand.getParentId()
                            )
                    )
            );
            goal.setGoal(parentGoal);
        } else {
            goal.setGoal(null);
        }

        return goalRepository.save(goal);
    }
}

