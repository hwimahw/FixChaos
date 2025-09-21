package ru.dhabits.fixchaos.planning.inbound.rest.goal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.getaboveparttreeandbelowalltree.mapper.GetAbovePartTreeAndBelowAllTreeMapper;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.getsubtree.mapper.GetSubtreeOfGoalMapper;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.getsubtree.response.GoalResponseDto;
import ru.dhabits.fixchaos.planning.usecase.goal.getaboveparttreeandbelowalltree.GetAbovePartTreeAndBelowAllTreeUseCase;
import ru.dhabits.fixchaos.planning.usecase.goal.getgoalsinperioddescentorder.GetGoalsInPeriodDecentOrderCase;
import ru.dhabits.fixchaos.planning.usecase.goal.getgoalsinperioddescentorder.mapper.GetGoalsInPeriodDecentOrderUseCaseMapper;
import ru.dhabits.fixchaos.planning.usecase.goal.getsubtree.GetSubtreeOfGoalUseCase;
import ru.dhabits.fixchaos.planning.usecase.goal.getsubtree.result.GoalResultDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/goal")
public class GoalController {

    private final GetSubtreeOfGoalUseCase getSubtreeUseCase;
    private final GetSubtreeOfGoalMapper getSubtreeMapper;

    private final GetAbovePartTreeAndBelowAllTreeUseCase getAbovePartTreeAndBelowAllTreeUseCase;
    private final GetAbovePartTreeAndBelowAllTreeMapper getAbovePartTreeAndBelowAllTreeMapper;

    private final GetGoalsInPeriodDecentOrderCase getGoalsInPeriodDecentOrderCase;
    private final GetGoalsInPeriodDecentOrderUseCaseMapper getGoalsInPeriodDecentOrderUseCaseMapper;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GoalResponseDto> getSubtree(@PathVariable("id") UUID id) {
        GoalResultDto goalResultDto = getSubtreeUseCase.getSubtreeOfNode(id);
        return ResponseEntity.ok(getSubtreeMapper.toGoalResponseDto(goalResultDto));
    }

    @GetMapping(value = "/abovebelow/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GoalResponseDto> getAbovePartTreeAndBelowAllTree(@PathVariable("id") UUID id) {
        GoalResultDto goalResultDto = getAbovePartTreeAndBelowAllTreeUseCase.getAbovePartTreeAndBelowAllTree(id);
        return ResponseEntity.ok(getAbovePartTreeAndBelowAllTreeMapper.toGoalResponseDto(goalResultDto));
    }

    @GetMapping(value = "/withperiod/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Goal> getGoalsInPeriodDecentOrder(@PathVariable("id") UUID id) {
        List<Goal> goals = getGoalsInPeriodDecentOrderCase.getGoalsInPeriodDecentOrder(id);
        return goals;
    }
}
