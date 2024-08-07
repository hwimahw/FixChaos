package ru.dhabits.fixchaos.trillioner.inbound.rest.shorttermgoal.updateshorttermgoal;

import com.dhabits.code.fixchaos.trillioner.dto.UpdateShortTermGoalRequestDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.updateshorttermgoal.request.UpdateShortTermGoalCommand;

@Mapper(componentModel = "spring")
public interface UpdateShortTermGoalMapper {
    UpdateShortTermGoalCommand toUpdateShortTermGoalCommand(UpdateShortTermGoalRequestDto updateShortTermGoalRequestDto);
}
