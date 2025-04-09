package ru.dhabits.fixchaos.planning.inbound.rest.longtermgoal.getlongtermgoal;

import com.dhabits.code.fixchaos.planning.dto.LongTermGoalResponseDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.planning.usecase.longtermgoal.getshorttermgoal.response.LongTermGoalInfo;

@Mapper(componentModel = "spring")
public interface GetLongTermGoalMapper {
    LongTermGoalResponseDto toLongTermGoalResponseDto(LongTermGoalInfo longTermGoalInfo);
}
