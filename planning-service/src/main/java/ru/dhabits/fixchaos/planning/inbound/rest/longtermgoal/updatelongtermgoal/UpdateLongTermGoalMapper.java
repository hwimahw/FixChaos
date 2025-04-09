package ru.dhabits.fixchaos.planning.inbound.rest.longtermgoal.updatelongtermgoal;

import com.dhabits.code.fixchaos.planning.dto.UpdateLongTermGoalRequestDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.planning.usecase.longtermgoal.updatelongtermgoal.request.UpdateLongTermGoalCommand;

@Mapper(componentModel = "spring")
public interface UpdateLongTermGoalMapper {
    UpdateLongTermGoalCommand toUpdateShortTermGoalCommand(UpdateLongTermGoalRequestDto updateLongTermGoalRequestDto);
}
