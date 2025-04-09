package ru.dhabits.fixchaos.planning.usecase.shorttermgoal.createshorttermgoal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dhabits.fixchaos.planning.domain.entity.ShortTermGoal;
import ru.dhabits.fixchaos.planning.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.createshorttermgoal.request.ShortTermGoalCommand;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.createshorttermgoal.response.ShortTermGoalInfo;

@Mapper(componentModel = "spring")
public interface CreateShortTermUseCaseMapper {
    @Mapping(target = "mainDirection", source = "mainDirection")
    @Mapping(target = "name", source = "shortTermGoalCommand.name")
    ShortTermGoal toShortTermGoal(ShortTermGoalCommand shortTermGoalCommand, MainDirection mainDirection);
    @Mapping(target = "mainDirection", source = "mainDirection.code")
    ShortTermGoalInfo toShortTermGoalInfo(ShortTermGoal shortTermGoal);
}
