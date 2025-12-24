package ru.dhabits.fixchaos.planning.inbound.rest.goal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.creategoal.mapper.CreateGoalMapper;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.creategoal.request.CreateGoalRequestDto;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.creategoal.response.CreateGoalResponseDto;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.getaboveparttreeandbelowalltree.mapper.GetAbovePartTreeAndBelowAllTreeMapper;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.getgoalsinperioddescentorder.GetGoalsInPeriodResponseDto;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.getgoalsinperioddescentorder.mapper.GetGoalsInPeriodMapper;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.getsubtree.mapper.GetSubtreeOfGoalMapper;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.getsubtree.response.GoalResponseDto;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.updategoal.mapper.UpdateGoalMapper;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.updategoal.request.UpdateGoalRequestDto;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.updategoal.response.UpdateGoalResponseDto;
import ru.dhabits.fixchaos.planning.usecase.goal.creategoal.CreateGoalUseCase;
import ru.dhabits.fixchaos.planning.usecase.goal.deletegoal.DeleteGoalUseCase;
import ru.dhabits.fixchaos.planning.usecase.goal.getaboveparttreeandbelowalltree.GetAbovePartTreeAndBelowAllTreeUseCase;
import ru.dhabits.fixchaos.planning.usecase.goal.getgoalsinperioddescentorder.GetGoalsInPeriodDecentOrderCase;
import ru.dhabits.fixchaos.planning.usecase.goal.getsubtree.GetSubtreeOfGoalUseCase;
import ru.dhabits.fixchaos.planning.usecase.goal.getsubtree.result.GoalResultDto;
import ru.dhabits.fixchaos.planning.usecase.goal.updategoal.UpdateGoalUseCase;

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
    private final GetGoalsInPeriodMapper getGoalsInPeriodMapper;

    private final CreateGoalUseCase createGoalUseCase;
    private final CreateGoalMapper createGoalMapper;

    private final UpdateGoalUseCase updateGoalUseCase;
    private final UpdateGoalMapper updateGoalMapper;

    private final DeleteGoalUseCase deleteGoalUseCase;

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
    public List<GetGoalsInPeriodResponseDto> getGoalsInPeriodDecentOrder(@PathVariable("id") UUID id) {
        List<Goal> goals = getGoalsInPeriodDecentOrderCase.getGoalsInPeriodDecentOrder(id);
        return getGoalsInPeriodMapper.toGetGoalsInPeriodResponseDto(goals);
    }

    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public CreateGoalResponseDto createGoal(@RequestBody CreateGoalRequestDto createGoalRequestDto) {
        Goal goal = createGoalUseCase.execute(createGoalMapper.toGoalCommand(createGoalRequestDto));
        CreateGoalResponseDto createGoalResponseDto = createGoalMapper.toGoalResponseDto(goal);
        return createGoalResponseDto;
    }

    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UpdateGoalResponseDto> updateGoal(
            @PathVariable("id") UUID id,
            @RequestBody UpdateGoalRequestDto updateGoalRequestDto
    ) {

        Goal goal = updateGoalUseCase.execute(
                updateGoalMapper.toUpdateGoalCommand(id, updateGoalRequestDto)
        );

        return ResponseEntity.ok(updateGoalMapper.toUpdateGoalResponseDto(goal));
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteGoal(@PathVariable("id") UUID id) {
        deleteGoalUseCase.execute(id);
        return ResponseEntity.status(204).build();
    }
}
