package ru.dhabits.fixchaos.planning.usecase.longtermgoal.createlongtermgoal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dhabits.fixchaos.planning.domain.entity.LongTermGoal;
import ru.dhabits.fixchaos.planning.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.planning.usecase.longtermgoal.createlongtermgoal.request.LongTermGoalCommand;
import ru.dhabits.fixchaos.planning.usecase.longtermgoal.createlongtermgoal.response.LongTermGoalResultDto;

@Mapper(componentModel = "spring")
public interface CreateLongTermUseCaseMapper {
    @Mapping(target = "mainDirection", source = "mainDirection")
    @Mapping(target = "name", source = "longTermGoalCommand.name")
    LongTermGoal toLongTermGoal(LongTermGoalCommand longTermGoalCommand, MainDirection mainDirection);
    @Mapping(target = "mainDirection", source = "mainDirection.code")
    LongTermGoalResultDto toLongTermGoalResultDto(LongTermGoal longTermGoal);
}
