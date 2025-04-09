package ru.dhabits.fixchaos.planning.inbound.rest.shorttermgoal.getshorttermgoal;

import com.dhabits.code.fixchaos.planning.dto.ShortTermGoalResponseDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.getshorttermgoal.response.ShortTermGoalInfo;

@Mapper(componentModel = "spring")
public interface GetShortTermGoalMapper {
    ShortTermGoalResponseDto toShortTermGoalResponseDto(ShortTermGoalInfo shortTermGoalDto);
}
