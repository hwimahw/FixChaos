package ru.dhabits.fixchaos.planning.inbound.rest.direction.getsubtree.mapper;

import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.planning.inbound.rest.direction.getsubtree.response.DirectionResponseDto;
import ru.dhabits.fixchaos.planning.usecase.direction.getsubtree.result.DirectionResultDto;

@Mapper(componentModel = "spring")
public interface GetSubtreeOfDirectionMapper {
    DirectionResponseDto toDirectionResponseDto(DirectionResultDto directionResultDto);
}
