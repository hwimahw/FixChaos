package ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.createshortterm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dhabits.fixchaos.trillioner.domain.entity.ShortTermGoal;
import ru.dhabits.fixchaos.trillioner.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.createshortterm.request.ShortTermGoalCommand;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.createshortterm.response.ShortTermGoalInfo;

@Mapper(componentModel = "spring")
public interface CreateShortTermUseCaseMapper {
    @Mapping(target = "mainDirection", source = "mainDirection")
    @Mapping(target = "name", source = "shortTermGoalCommand.name")
    ShortTermGoal toShortTermGoal(ShortTermGoalCommand shortTermGoalCommand, MainDirection mainDirection);
    @Mapping(target = "mainDirection", source = "mainDirection.code")
    ShortTermGoalInfo toShortTermGoalInfo(ShortTermGoal shortTermGoal);
}
