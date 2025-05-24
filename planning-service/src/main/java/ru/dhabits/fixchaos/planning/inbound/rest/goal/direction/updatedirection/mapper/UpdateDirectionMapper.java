package ru.dhabits.fixchaos.planning.inbound.rest.goal.direction.updatedirection.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.direction.updatedirection.request.UpdateDirectionRequestDto;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.direction.updatedirection.response.UpdateDirectionResponseDto;
import ru.dhabits.fixchaos.planning.usecase.direction.updatedirection.command.UpdateDirectionCommand;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UpdateDirectionMapper {

    @Mapping(target = "parentDirection", source = "direction")
    UpdateDirectionResponseDto toUpdateDirectionResponseDto(Direction direction);

    UpdateDirectionCommand toUpdateDirectionCommand(UUID id, UpdateDirectionRequestDto updateDirectionRequestDto);
}
