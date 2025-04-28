package ru.dhabits.fixchaos.planning.inbound.rest.goal.getsubtree;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.getaboveparttreeandbelowalltree.mapper.GetAbovePartTreeAndBelowAllTreeMapper;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.getsubtree.mapper.GetSubtreeMapper;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.getsubtree.response.GoalResponseDto;
import ru.dhabits.fixchaos.planning.usecase.goal.getaboveparttreeandbelowalltree.GetAbovePartTreeAndBelowAllTreeUseCase;
import ru.dhabits.fixchaos.planning.usecase.goal.getsubtree.GetSubtreeUseCase;
import ru.dhabits.fixchaos.planning.usecase.goal.getsubtree.result.GoalResultDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/goal")
public class GoalController {

    private final GetSubtreeUseCase getSubtreeUseCase;
    private final GetSubtreeMapper getSubtreeMapper;

    private final GetAbovePartTreeAndBelowAllTreeUseCase getAbovePartTreeAndBelowAllTreeUseCase;
    private final GetAbovePartTreeAndBelowAllTreeMapper getAbovePartTreeAndBelowAllTreeMapper;


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
}
