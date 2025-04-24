package ru.dhabits.fixchaos.planning.usecase.goal.getsubtree;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;
import ru.dhabits.fixchaos.planning.domain.repository.GoalRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.usecase.goal.getsubtree.mapper.GetSubtreeUseCaseMapper;
import ru.dhabits.fixchaos.planning.usecase.goal.getsubtree.result.GoalResultDto;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetSubtreeUseCase {

    private final GoalRepository goalRepository;
    private final GetSubtreeUseCaseMapper getSubtreeUseCaseMapper;

    public GoalResultDto getSubtreeOfNode(UUID id) {
        Goal goal = goalRepository.findById(id).orElseThrow(
                () -> new EntityAlreadyExistsOrDoesNotExistException(String.format("Цель с id %s не найдена", id))
        );
        GoalResultDto goalResultDto = getSubtreeUseCaseMapper.toGoalResultDto(goal);
        getSubtreeOfNodeIter(goal, goalResultDto);
        return goalResultDto;
    }

    private void getSubtreeOfNodeIter(Goal goal, GoalResultDto goalResultDto) {
        List<Goal> childrenGoals = goal.getGoals();
        if (childrenGoals != null) {
            goalResultDto.setGoals(getSubtreeUseCaseMapper.toGoalResultDtos(childrenGoals));
            for (Goal child : childrenGoals) {
                getSubtreeOfNodeIter(child, getSubtreeUseCaseMapper.toGoalResultDto(goal));
            }
        }
    }
}
