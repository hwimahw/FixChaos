package ru.dhabits.fixchaos.planning.usecase.longtermgoal.getshorttermgoal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dhabits.fixchaos.planning.domain.entity.LongTermGoal;
import ru.dhabits.fixchaos.planning.usecase.longtermgoal.getshorttermgoal.response.LongTermGoalInfo;

@Mapper(componentModel = "spring")
public interface GetLongTermUseCaseMapper {
    @Mapping(target = "mainDirection", source = "mainDirection.code")
    LongTermGoalInfo toLongTermGoalInfo(LongTermGoal longTermGoal);
}
