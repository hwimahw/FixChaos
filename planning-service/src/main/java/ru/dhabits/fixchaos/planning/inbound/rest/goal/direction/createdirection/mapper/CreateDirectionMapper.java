package ru.dhabits.fixchaos.planning.inbound.rest.goal.direction.createdirection.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.direction.createdirection.request.CreateDirectionRequestDto;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.direction.createdirection.response.CreateDirectionResponseDto;
import ru.dhabits.fixchaos.planning.usecase.direction.createdirection.command.CreateDirectionCommand;

@Mapper(componentModel = "spring")
public interface CreateDirectionMapper {

    @Mapping(target = "parentDirection", source = "direction")
    CreateDirectionResponseDto toDirectionResponseDto(Direction direction);

    CreateDirectionCommand toDirectionCommand(CreateDirectionRequestDto directionRequestDto);
}
