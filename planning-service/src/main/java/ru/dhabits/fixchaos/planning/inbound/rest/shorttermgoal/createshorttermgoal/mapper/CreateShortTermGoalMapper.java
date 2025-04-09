package ru.dhabits.fixchaos.planning.inbound.rest.shorttermgoal.createshorttermgoal.mapper;

import com.dhabits.code.fixchaos.planning.dto.ShortTermGoalRequestDto;
import com.dhabits.code.fixchaos.planning.dto.ShortTermGoalResponseDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.createshorttermgoal.request.ShortTermGoalCommand;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.createshorttermgoal.response.ShortTermGoalInfo;

@Mapper(componentModel = "spring")
public interface CreateShortTermGoalMapper {
    ShortTermGoalCommand toShortTermGoalCommand(ShortTermGoalRequestDto shortTermGoalDto);
    ShortTermGoalResponseDto toShortTermGoalResponseDto(ShortTermGoalInfo shortTermGoalDto);
}
