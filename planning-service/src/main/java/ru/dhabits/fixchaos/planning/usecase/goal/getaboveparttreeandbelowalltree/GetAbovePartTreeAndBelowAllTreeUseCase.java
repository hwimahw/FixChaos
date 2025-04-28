package ru.dhabits.fixchaos.planning.usecase.goal.getaboveparttreeandbelowalltree;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;
import ru.dhabits.fixchaos.planning.domain.repository.GoalRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.usecase.goal.getaboveparttreeandbelowalltree.mapper.GetAbovePartTreeAndBelowAllTreeUseCaseMapper;
import ru.dhabits.fixchaos.planning.usecase.goal.getsubtree.result.GoalResultDto;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetAbovePartTreeAndBelowAllTreeUseCase {

    private final GoalRepository goalRepository;
    private final GetAbovePartTreeAndBelowAllTreeUseCaseMapper getAbovePartTreeAndBelowAllTreeUseCaseMapper;

    public GoalResultDto getAbovePartTreeAndBelowAllTree(UUID id) {
        Goal goal = goalRepository.findById(id).orElseThrow(
                () -> new EntityAlreadyExistsOrDoesNotExistException(String.format("Цель с id %s не найдена", id))
        );
        GoalResultDto goalResultDto = getAbovePartTreeAndBelowAllTreeUseCaseMapper.toGoalResultDto(goal);
        getBelowAllTreeIter(goal, goalResultDto);
        GoalResultDto primaryNode = getAbovePartTreeIter(goal, goalResultDto);
        return primaryNode;
    }

    private GoalResultDto getAbovePartTreeIter(Goal goal, GoalResultDto goalResultDto) {
        Goal parentGoal = goal.getGoal();
        if (parentGoal == null) {
            return goalResultDto;
        } else {
            GoalResultDto parentGoalResultDto = getAbovePartTreeAndBelowAllTreeUseCaseMapper.toGoalResultDto(parentGoal);
            parentGoalResultDto.setGoals(List.of(goalResultDto));
            return getAbovePartTreeIter(parentGoal, parentGoalResultDto);
        }
    }

    private void getBelowAllTreeIter(Goal goal, GoalResultDto goalResultDto) {
        List<Goal> childrenGoals = goal.getGoals();
        if (childrenGoals != null) {
            goalResultDto.setGoals(getAbovePartTreeAndBelowAllTreeUseCaseMapper.toGoalResultDtos(childrenGoals));
            for (Goal child : childrenGoals) {
                getBelowAllTreeIter(child, getAbovePartTreeAndBelowAllTreeUseCaseMapper.toGoalResultDto(goal));
            }
        }
    }
}
