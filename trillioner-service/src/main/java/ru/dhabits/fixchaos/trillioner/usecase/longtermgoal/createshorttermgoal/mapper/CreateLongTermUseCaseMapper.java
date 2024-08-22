package ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.createshorttermgoal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dhabits.fixchaos.trillioner.domain.entity.LongTermGoal;
import ru.dhabits.fixchaos.trillioner.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.createshorttermgoal.request.LongTermGoalCommand;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.createshorttermgoal.response.LongTermGoalResultDto;

@Mapper(componentModel = "spring")
public interface CreateLongTermUseCaseMapper {
    @Mapping(target = "mainDirection", source = "mainDirection")
    @Mapping(target = "name", source = "longTermGoalCommand.name")
    LongTermGoal toLongTermGoal(LongTermGoalCommand longTermGoalCommand, MainDirection mainDirection);
    @Mapping(target = "mainDirection", source = "mainDirection.code")
    LongTermGoalResultDto toLongTermGoalResultDto(LongTermGoal longTermGoal);
}
