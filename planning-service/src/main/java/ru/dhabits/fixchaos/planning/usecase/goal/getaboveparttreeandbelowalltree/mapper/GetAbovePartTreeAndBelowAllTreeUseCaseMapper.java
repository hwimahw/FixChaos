package ru.dhabits.fixchaos.planning.usecase.goal.getaboveparttreeandbelowalltree.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;
import ru.dhabits.fixchaos.planning.usecase.goal.getsubtree.result.GoalResultDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GetAbovePartTreeAndBelowAllTreeUseCaseMapper {
    @Mapping(target = "direction", source = "direction.code")
    GoalResultDto toGoalResultDto(Goal goal);

    List<GoalResultDto> toGoalResultDtos(List<Goal> goals);
}
