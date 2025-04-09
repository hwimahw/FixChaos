package ru.dhabits.fixchaos.planning.inbound.rest.shorttermgoal.updateshorttermgoal;

import com.dhabits.code.fixchaos.planning.dto.UpdateShortTermGoalRequestDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.planning.usecase.shorttermgoal.updateshorttermgoal.request.UpdateShortTermGoalCommand;

@Mapper(componentModel = "spring")
public interface UpdateShortTermGoalMapper {
    UpdateShortTermGoalCommand toUpdateShortTermGoalCommand(UpdateShortTermGoalRequestDto updateShortTermGoalRequestDto);
}
