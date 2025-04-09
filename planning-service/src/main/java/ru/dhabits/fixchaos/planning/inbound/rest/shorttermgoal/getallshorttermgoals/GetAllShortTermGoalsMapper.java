package ru.dhabits.fixchaos.planning.inbound.rest.shorttermgoal.getallshorttermgoals;

import com.dhabits.code.fixchaos.planning.dto.ShortTermGoalListResponseDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.getallshorttermgoals.response.ShortTermGoalListResultDto;

@Mapper(componentModel = "spring")
public interface GetAllShortTermGoalsMapper {
    ShortTermGoalListResponseDto toShortTermGoalResponseDto(ShortTermGoalListResultDto shortTermGoalListResultDto);
}
