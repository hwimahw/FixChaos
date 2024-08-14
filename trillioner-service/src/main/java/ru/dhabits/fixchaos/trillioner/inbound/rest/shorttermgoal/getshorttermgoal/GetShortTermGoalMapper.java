package ru.dhabits.fixchaos.trillioner.inbound.rest.shorttermgoal.getshorttermgoal;

import com.dhabits.code.fixchaos.trillioner.dto.ShortTermGoalResponseDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.getshorttermgoal.response.ShortTermGoalInfo;

@Mapper(componentModel = "spring")
public interface GetShortTermGoalMapper {
    ShortTermGoalResponseDto toShortTermGoalResponseDto(ShortTermGoalInfo shortTermGoalDto);
}
