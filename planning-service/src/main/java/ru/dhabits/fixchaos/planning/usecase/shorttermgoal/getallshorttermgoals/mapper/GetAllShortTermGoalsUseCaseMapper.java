package ru.dhabits.fixchaos.planning.usecase.shorttermgoal.getallshorttermgoals.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import ru.dhabits.fixchaos.planning.domain.entity.ShortTermGoal;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.getallshorttermgoals.response.ShortTermGoalInfo;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.getallshorttermgoals.response.ShortTermGoalListResultDto;

@Mapper(componentModel = "spring")
public interface GetAllShortTermGoalsUseCaseMapper {
    ShortTermGoalListResultDto toShortTermGoalListResponseDto(Page<ShortTermGoal> page);

    @Mapping(target = "mainDirection", source = "mainDirection.code")
    ShortTermGoalInfo toShortTermGoalInfo(ShortTermGoal shortTermGoal);
}
