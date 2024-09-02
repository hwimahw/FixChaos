package ru.dhabits.fixchaos.trillioner.inbound.rest.longtermgoal.createlongtermgoal.mapper;

import com.dhabits.code.fixchaos.trillioner.dto.LongTermGoalRequestDto;
import com.dhabits.code.fixchaos.trillioner.dto.LongTermGoalResponseDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.createlongtermgoal.request.LongTermGoalCommand;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.createlongtermgoal.response.LongTermGoalResultDto;

@Mapper(componentModel = "spring")
public interface CreateLongTermGoalMapper {
    LongTermGoalCommand toLongTermGoalCommand(LongTermGoalRequestDto longTermGoalDto);
    LongTermGoalResponseDto toLongTermGoalResponseDto(LongTermGoalResultDto longTermGoalResultDto);
}
