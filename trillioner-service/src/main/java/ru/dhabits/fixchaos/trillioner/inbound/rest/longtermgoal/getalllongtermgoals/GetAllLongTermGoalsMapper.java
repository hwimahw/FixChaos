package ru.dhabits.fixchaos.trillioner.inbound.rest.longtermgoal.getalllongtermgoals;

import com.dhabits.code.fixchaos.trillioner.dto.LongTermGoalListResponseDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.getalllongtermgoals.response.LongTermGoalListResultDto;

@Mapper(componentModel = "spring")
public interface GetAllLongTermGoalsMapper {
    LongTermGoalListResponseDto toShortTermGoalResponseDto(LongTermGoalListResultDto longTermGoalListResultDto);
}
