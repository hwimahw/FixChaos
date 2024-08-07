package ru.dhabits.fixchaos.trillioner.inbound.rest.shorttermgoal.createshorttermgoal.mapper;

import com.dhabits.code.fixchaos.trillioner.dto.ShortTermGoalRequestDto;
import com.dhabits.code.fixchaos.trillioner.dto.ShortTermGoalResponseDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.createshorttermgoal.request.ShortTermGoalCommand;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.createshorttermgoal.response.ShortTermGoalInfo;

@Mapper(componentModel = "spring")
public interface CreateShortTermGoalMapper {
    ShortTermGoalCommand toShortTermGoalCommand(ShortTermGoalRequestDto shortTermGoalDto);
    ShortTermGoalResponseDto toShortTermGoalResponseDto(ShortTermGoalInfo shortTermGoalDto);
}
