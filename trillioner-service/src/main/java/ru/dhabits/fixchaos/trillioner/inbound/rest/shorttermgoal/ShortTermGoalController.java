package ru.dhabits.fixchaos.trillioner.inbound.rest.shorttermgoal;

import com.dhabits.code.fixchaos.trillioner.controller.ShortTermGoalApi;

import com.dhabits.code.fixchaos.trillioner.dto.ShortTermGoalRequestDto;
import com.dhabits.code.fixchaos.trillioner.dto.ShortTermGoalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.dhabits.fixchaos.trillioner.inbound.rest.shorttermgoal.createshorttermgoal.mapper.CreateShortTermGoalMapper;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.createshortterm.CreateShortTermUseCase;

@Controller
@RequiredArgsConstructor
public class ShortTermGoalController implements ShortTermGoalApi {

    private final CreateShortTermGoalMapper createShortTermGoalMapper;
    private final CreateShortTermUseCase createShortTermUseCase;

    @Override
    public ResponseEntity<ShortTermGoalResponseDto> createShortTermGoal(ShortTermGoalRequestDto shortTermGoalRequestDto) {
        var shortTermGoalCommand = createShortTermGoalMapper.toShortTermGoalCommand(shortTermGoalRequestDto);
        var shortTermGoalInfo = createShortTermUseCase.execute(shortTermGoalCommand);
        return ResponseEntity.ok(createShortTermGoalMapper.toShortTermGoalResponseDto(shortTermGoalInfo));
    }
}
