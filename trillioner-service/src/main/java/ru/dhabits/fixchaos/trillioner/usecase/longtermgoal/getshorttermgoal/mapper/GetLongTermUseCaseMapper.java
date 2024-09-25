package ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.getshorttermgoal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dhabits.fixchaos.trillioner.domain.entity.LongTermGoal;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.getshorttermgoal.response.LongTermGoalInfo;

@Mapper(componentModel = "spring")
public interface GetLongTermUseCaseMapper {
    @Mapping(target = "mainDirection", source = "mainDirection.code")
    LongTermGoalInfo toLongTermGoalInfo(LongTermGoal longTermGoal);
}
