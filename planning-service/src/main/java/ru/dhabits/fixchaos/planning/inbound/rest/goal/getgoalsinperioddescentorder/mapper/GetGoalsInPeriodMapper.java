package ru.dhabits.fixchaos.planning.inbound.rest.goal.getgoalsinperioddescentorder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.getgoalsinperioddescentorder.GetGoalsInPeriodResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GetGoalsInPeriodMapper {

    @Mapping(target = "goalType", expression = "java(goal.getGoalType().name())")
    @Mapping(target = "direction", expression = "java(goal.getDirection().getName())")
    GetGoalsInPeriodResponseDto toGetGoalInPeriodResponseDto(Goal goal);

    List<GetGoalsInPeriodResponseDto> toGetGoalsInPeriodResponseDto(List<Goal> goals);
}
