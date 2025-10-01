package ru.dhabits.fixchaos.planning.usecase.goal.creategoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;
import ru.dhabits.fixchaos.planning.domain.repository.DirectionRepository;
import ru.dhabits.fixchaos.planning.domain.repository.GoalRepository;
import ru.dhabits.fixchaos.planning.enumeration.GoalType;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.usecase.goal.creategoal.command.CreateGoalCommand;

@Component
@RequiredArgsConstructor
public class CreateGoalUseCase {

    private final GoalRepository goalRepository;
    private final DirectionRepository directionRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Goal execute(CreateGoalCommand goalCommand) {
        Goal parentGoal = null;
        if (goalCommand.getParentId() != null) {
            parentGoal = goalRepository.findById(goalCommand.getParentId())
                    .orElseThrow(
                            () -> {
                                throw new EntityAlreadyExistsOrDoesNotExistException(
                                        String.format(
                                                "Такой родительской цели с id %s не существует",
                                                goalCommand.getParentId()
                                        ));
                            }
                    );
        }
        Direction direction = null;
        if (goalCommand.getDirectionId() == null) {
            throw new EntityAlreadyExistsOrDoesNotExistException("При создании цели отсутствует идентификатор направления");
        } else {
            direction = directionRepository.findById(goalCommand.getDirectionId())
                    .orElseThrow(
                            () -> {
                                throw new EntityAlreadyExistsOrDoesNotExistException(
                                        String.format(
                                                "Такого направления с id %s не существует",
                                                goalCommand.getDirectionId()
                                        )
                                );
                            }
                    );
        }
        Goal goal = new Goal()
                .setName(goalCommand.getName())
                .setDirection(direction)
                .setStartDate(goalCommand.getStartDate())
                .setEndDate(goalCommand.getEndDate())
                .setGoal(parentGoal)
                .setGoalType(GoalType.valueOf(goalCommand.getGoalType()));
        return goalRepository.save(goal);
    }
}
