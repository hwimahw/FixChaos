package ru.dhabits.fixchaos.planning.inbound.rest.goal.getaboveparttreeandbelowalltree.mapper;

import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.getsubtree.response.GoalResponseDto;
import ru.dhabits.fixchaos.planning.usecase.goal.getsubtree.result.GoalResultDto;

@Mapper(componentModel = "spring")
public interface GetAbovePartTreeAndBelowAllTreeMapper {
    GoalResponseDto toGoalResponseDto(GoalResultDto goalResultDto);
}
