package ru.dhabits.fixchaos.planning.inbound.rest.goal.createdirection.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.createdirection.request.DirectionRequestDto;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.createdirection.response.DirectionResponseDto;
import ru.dhabits.fixchaos.planning.usecase.direction.createdirection.command.DirectionCommand;

@Mapper(componentModel = "spring")
public interface CreateDirectionMapper {

    @Mapping(target = "parentDirection", source = "direction")
    DirectionResponseDto toDirectionResponseDto(Direction direction);

    DirectionCommand toDirectionCommand(DirectionRequestDto directionRequestDto);
}
