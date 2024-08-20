package ru.dhabits.fixchaos.trillioner.inbound.rest.shorttermgoal.getallshorttermgoals;

import com.dhabits.code.fixchaos.trillioner.dto.ShortTermGoalListResponseDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.getallshorttermgoals.response.ShortTermGoalListResultDto;

@Mapper(componentModel = "spring")
public interface GetAllShortTermGoalsMapper {
    ShortTermGoalListResponseDto toShortTermGoalResponseDto(ShortTermGoalListResultDto shortTermGoalListResultDto);
}
