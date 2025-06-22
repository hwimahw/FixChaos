package ru.dhabits.fixchaos.planning.usecase.direction.getalltrees;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.domain.repository.DirectionRepository;
import ru.dhabits.fixchaos.planning.usecase.direction.getsubtree.GetSubtreeOfDirectionUseCase;
import ru.dhabits.fixchaos.planning.usecase.direction.getsubtree.result.DirectionResultDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllTreesOfDirectionsUseCase {

    private final GetSubtreeOfDirectionUseCase getSubtreeOfDirectionUseCase;
    private final DirectionRepository directionRepository;

    public List<DirectionResultDto> getAllTrees() {
        List<Direction> rootDirections = directionRepository.findByDirectionIsNull();
        List<DirectionResultDto> directionResultDtos = new ArrayList<>();
        for (Direction rootDirection : rootDirections) {
            directionResultDtos.add(getSubtreeOfDirectionUseCase.getSubtreeOfNode(rootDirection.getId()));
        }
        return directionResultDtos;
    }
}
