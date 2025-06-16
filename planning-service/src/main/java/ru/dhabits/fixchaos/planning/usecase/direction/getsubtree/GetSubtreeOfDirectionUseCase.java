package ru.dhabits.fixchaos.planning.usecase.direction.getsubtree;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.domain.repository.DirectionRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.usecase.direction.getsubtree.mapper.GetSubtreeOfDirectionUseCaseMapper;
import ru.dhabits.fixchaos.planning.usecase.direction.getsubtree.result.DirectionResultDto;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetSubtreeOfDirectionUseCase {

    private final DirectionRepository directionRepository;
    private final GetSubtreeOfDirectionUseCaseMapper getSubtreeUseCaseMapper;

    public DirectionResultDto getSubtreeOfNode(UUID id) {
        Direction direction = directionRepository.findById(id).orElseThrow(
                () -> new EntityAlreadyExistsOrDoesNotExistException(String.format("Направление с id %s не найдена", id))
        );
        DirectionResultDto directionResultDto = getSubtreeUseCaseMapper.toDirectionResultDto(direction);
        getSubtreeOfNodeIter(direction, directionResultDto);
        return directionResultDto;
    }

    private void getSubtreeOfNodeIter(Direction direction, DirectionResultDto directionResultDto) {
        List<Direction> childrenDirections = direction.getDirections();
        if (childrenDirections != null) {
            directionResultDto.setDirections(getSubtreeUseCaseMapper.toDirectionResultDtos(childrenDirections));
            for (Direction child : childrenDirections) {
                getSubtreeOfNodeIter(child, getSubtreeUseCaseMapper.toDirectionResultDto(direction));
            }
        }
    }
}
