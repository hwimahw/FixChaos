package ru.dhabits.fixchaos.planning.inbound.rest.goal.updategoal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;import ru.dhabits.fixchaos.planning.domain.entity.Goal;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.updategoal.request.UpdateGoalRequestDto;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.updategoal.response.UpdateGoalResponseDto;
import ru.dhabits.fixchaos.planning.usecase.goal.updategoal.command.UpdateGoalCommand;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UpdateGoalMapper {

    @Mapping(target = "parentDirection", source = "direction")
    UpdateGoalResponseDto toUpdateGoalResponseDto(Goal goal);

    UpdateGoalCommand toUpdateGoalCommand(UUID id, UpdateGoalRequestDto updateGoalRequestDto);
}
