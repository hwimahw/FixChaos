package ru.dhabits.fixchaos.trillioner.inbound.rest.longtermgoal;

import com.dhabits.code.fixchaos.trillioner.controller.LongTermGoalApi;
import com.dhabits.code.fixchaos.trillioner.dto.LongTermGoalRequestDto;
import com.dhabits.code.fixchaos.trillioner.dto.LongTermGoalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.dhabits.fixchaos.trillioner.inbound.rest.longtermgoal.createlongtermgoal.mapper.CreateLongTermGoalMapper;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.createshorttermgoal.CreateLongTermGoalUseCase;

@Controller
@RequiredArgsConstructor
public class LongTermGoalController implements LongTermGoalApi {

    private final CreateLongTermGoalUseCase createLongTermGoalUseCase;
    private final CreateLongTermGoalMapper createLongTermGoalMapper;

    @Override
    public ResponseEntity<LongTermGoalResponseDto> createLongTermGoal(LongTermGoalRequestDto longTermGoalRequestDto) {
        var longTermGoalCommand = createLongTermGoalMapper.toLongTermGoalCommand(longTermGoalRequestDto);
        var longTermGoalResultDto = createLongTermGoalUseCase.execute(longTermGoalCommand);
        return ResponseEntity.ok(createLongTermGoalMapper.toLongTermGoalResponseDto(longTermGoalResultDto));
    }
}
