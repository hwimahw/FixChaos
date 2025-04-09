package ru.dhabits.fixchaos.planning.usecase.shorttermgoal.getshorttermgoal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dhabits.fixchaos.planning.domain.entity.ShortTermGoal;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.getshorttermgoal.response.ShortTermGoalInfo;

@Mapper(componentModel = "spring")
public interface GetShortTermUseCaseMapper {
    @Mapping(target = "mainDirection", source = "mainDirection.code")
    ShortTermGoalInfo toShortTermGoalInfo(ShortTermGoal shortTermGoal);
}
