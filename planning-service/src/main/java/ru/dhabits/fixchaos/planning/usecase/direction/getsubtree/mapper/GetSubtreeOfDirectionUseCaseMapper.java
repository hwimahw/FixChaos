package ru.dhabits.fixchaos.planning.usecase.direction.getsubtree.mapper;

import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.usecase.direction.getsubtree.result.DirectionResultDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GetSubtreeOfDirectionUseCaseMapper {

    DirectionResultDto toDirectionResultDto(Direction direction);

    List<DirectionResultDto> toDirectionResultDtos(List<Direction> directions);
}
