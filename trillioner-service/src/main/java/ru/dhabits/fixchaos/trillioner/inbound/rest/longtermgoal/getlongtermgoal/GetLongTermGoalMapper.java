package ru.dhabits.fixchaos.trillioner.inbound.rest.longtermgoal.getlongtermgoal;

import com.dhabits.code.fixchaos.trillioner.dto.LongTermGoalResponseDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.getshorttermgoal.response.LongTermGoalInfo;

@Mapper(componentModel = "spring")
public interface GetLongTermGoalMapper {
    LongTermGoalResponseDto toLongTermGoalResponseDto(LongTermGoalInfo longTermGoalInfo);
}
