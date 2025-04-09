package ru.dhabits.fixchaos.planning.inbound.rest.longtermgoal.createlongtermgoal.mapper;

import com.dhabits.code.fixchaos.planning.dto.LongTermGoalRequestDto;
import com.dhabits.code.fixchaos.planning.dto.LongTermGoalResponseDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.planning.usecase.longtermgoal.createlongtermgoal.request.LongTermGoalCommand;
import ru.dhabits.fixchaos.planning.usecase.longtermgoal.createlongtermgoal.response.LongTermGoalResultDto;

@Mapper(componentModel = "spring")
public interface CreateLongTermGoalMapper {
    LongTermGoalCommand toLongTermGoalCommand(LongTermGoalRequestDto longTermGoalDto);
    LongTermGoalResponseDto toLongTermGoalResponseDto(LongTermGoalResultDto longTermGoalResultDto);
}
