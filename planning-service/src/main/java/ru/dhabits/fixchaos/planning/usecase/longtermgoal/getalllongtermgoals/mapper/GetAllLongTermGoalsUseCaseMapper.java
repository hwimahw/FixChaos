package ru.dhabits.fixchaos.planning.usecase.longtermgoal.getalllongtermgoals.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import ru.dhabits.fixchaos.planning.domain.entity.LongTermGoal;
import ru.dhabits.fixchaos.planning.usecase.longtermgoal.getalllongtermgoals.response.LongTermGoalInfo;
import ru.dhabits.fixchaos.planning.usecase.longtermgoal.getalllongtermgoals.response.LongTermGoalListResultDto;

@Mapper(componentModel = "spring")
public interface GetAllLongTermGoalsUseCaseMapper {
    LongTermGoalListResultDto toLongTermGoalListResponseDto(Page<LongTermGoal> page);

    @Mapping(target = "mainDirection", source = "mainDirection.code")
    LongTermGoalInfo toLongTermGoalInfo(LongTermGoal longTermGoal);
}
