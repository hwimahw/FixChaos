package ru.dhabits.fixchaos.planning.inbound.rest.goal.creategoal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.creategoal.request.CreateGoalRequestDto;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.creategoal.response.CreateGoalResponseDto;
import ru.dhabits.fixchaos.planning.usecase.goal.creategoal.command.CreateGoalCommand;

@Mapper(componentModel = "spring")
public interface CreateGoalMapper {

    @Mapping(target = "parentGoal", source = "goal")
    @Mapping(target = "direction", expression = "java(toDirection(goal.getDirection()))")
    CreateGoalResponseDto toGoalResponseDto(Goal goal);

    CreateGoalCommand toGoalCommand(CreateGoalRequestDto goalRequestDto);

    default String toDirection(Direction direction) {
        if (direction != null) {
            return direction.getCode();
        }
        return null;
    }
}