package ru.dhabits.fixchaos.planning.inbound.rest.longtermgoal.getalllongtermgoals;

import com.dhabits.code.fixchaos.planning.dto.LongTermGoalListResponseDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.planning.usecase.longtermgoal.getalllongtermgoals.response.LongTermGoalListResultDto;

@Mapper(componentModel = "spring")
public interface GetAllLongTermGoalsMapper {
    LongTermGoalListResponseDto toShortTermGoalResponseDto(LongTermGoalListResultDto longTermGoalListResultDto);
}
