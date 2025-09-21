package ru.dhabits.fixchaos.planning.usecase.goal.getgoalsinperioddescentorder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;
import ru.dhabits.fixchaos.planning.domain.repository.DirectionRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.usecase.goal.getgoalsinperioddescentorder.mapper.GetGoalsInPeriodDecentOrderUseCaseMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetGoalsInPeriodDecentOrderCase {

    private final DirectionRepository directionRepository;
    private final GetGoalsInPeriodDecentOrderUseCaseMapper getGoalsInPeriodDecentOrderUseCaseMapper;

    public List<Goal> getGoalsInPeriodDecentOrder(UUID id) {
        List<Goal> allGoals = walkOnDirectionsAndGetAllGoals(id);
        List<Goal> copyAllGoals = new ArrayList<>(allGoals);
        for (Goal goal : allGoals) {
            walkOnGoalsAndDeleteThemFromAllGoals(goal, copyAllGoals);
        }

        return copyAllGoals;
    }

    public List<Goal> walkOnGoalsAndDeleteThemFromAllGoals(Goal goal, List<Goal> allGoals) {
        List<Goal> childrenGoals = goal.getGoals();
        allGoals.removeAll(childrenGoals);
        for (Goal childGoal : childrenGoals) {
            walkOnGoalsAndDeleteThemFromAllGoals(childGoal, allGoals);
        }
        return allGoals;
    }


    public List<Goal> walkOnDirectionsAndGetAllGoals(UUID id) {
        Direction direction = directionRepository.findById(id).orElseThrow(
                () -> new EntityAlreadyExistsOrDoesNotExistException(String.format("Направление с id %s не найдено", id))
        );
        List<Goal> allGoals = new ArrayList<>();
        List<Direction> childrenDirections = direction.getDirections();
        for (Direction child : childrenDirections) {
            walkOnDirectionsAndGetAllGoalsIter(direction, allGoals);
        }
        return allGoals;
    }

    private void walkOnDirectionsAndGetAllGoalsIter(Direction direction, List<Goal> allGoals) {
        allGoals.addAll(direction.getGoals());
        List<Direction> childrenDirections = direction.getDirections();
        for (Direction child : childrenDirections) {
            walkOnDirectionsAndGetAllGoalsIter(child, allGoals);
        }
    }
}
