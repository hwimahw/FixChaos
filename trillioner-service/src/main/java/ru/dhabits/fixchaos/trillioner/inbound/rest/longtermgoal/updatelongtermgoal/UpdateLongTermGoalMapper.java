package ru.dhabits.fixchaos.trillioner.inbound.rest.longtermgoal.updatelongtermgoal;

import com.dhabits.code.fixchaos.trillioner.dto.UpdateLongTermGoalRequestDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.updatelongtermgoal.request.UpdateLongTermGoalCommand;

@Mapper(componentModel = "spring")
public interface UpdateLongTermGoalMapper {
    UpdateLongTermGoalCommand toUpdateShortTermGoalCommand(UpdateLongTermGoalRequestDto updateLongTermGoalRequestDto);
}
