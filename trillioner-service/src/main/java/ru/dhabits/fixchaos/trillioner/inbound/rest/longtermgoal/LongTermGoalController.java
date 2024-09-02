package ru.dhabits.fixchaos.trillioner.inbound.rest.longtermgoal;

import com.dhabits.code.fixchaos.trillioner.controller.LongTermGoalApi;
import com.dhabits.code.fixchaos.trillioner.dto.LongTermGoalRequestDto;
import com.dhabits.code.fixchaos.trillioner.dto.LongTermGoalResponseDto;
import com.dhabits.code.fixchaos.trillioner.dto.UpdateLongTermGoalRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.dhabits.fixchaos.trillioner.inbound.rest.longtermgoal.createlongtermgoal.mapper.CreateLongTermGoalMapper;
import ru.dhabits.fixchaos.trillioner.inbound.rest.longtermgoal.updatelongtermgoal.UpdateLongTermGoalMapper;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.createlongtermgoal.CreateLongTermGoalUseCase;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.deleteshorttermgoal.DeleteLongTermGoalUseCase;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.updatelongtermgoal.UpdateLongTermGoalUseCase;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class LongTermGoalController implements LongTermGoalApi {

    private final CreateLongTermGoalUseCase createLongTermGoalUseCase;
    private final CreateLongTermGoalMapper createLongTermGoalMapper;

    private final UpdateLongTermGoalUseCase updateLongTermGoalUseCase;
    private final UpdateLongTermGoalMapper updateLongTermGoalMapper;

    private final DeleteLongTermGoalUseCase deleteLongTermGoalUseCase;


    @Override
    public ResponseEntity<LongTermGoalResponseDto> createLongTermGoal(LongTermGoalRequestDto longTermGoalRequestDto) {
        var longTermGoalCommand = createLongTermGoalMapper.toLongTermGoalCommand(longTermGoalRequestDto);
        var longTermGoalResultDto = createLongTermGoalUseCase.execute(longTermGoalCommand);
        return ResponseEntity.ok(createLongTermGoalMapper.toLongTermGoalResponseDto(longTermGoalResultDto));
    }

    @Override
    public ResponseEntity<Void> deleteLongTermGoal(UUID id) {
        deleteLongTermGoalUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateLongTermGoal(UUID id, UpdateLongTermGoalRequestDto updateLongTermGoalRequestDto) {
        var updateLongTermGoalCommand = updateLongTermGoalMapper.toUpdateShortTermGoalCommand(updateLongTermGoalRequestDto);
        updateLongTermGoalUseCase.execute(id, updateLongTermGoalCommand);
        return ResponseEntity.ok().build();

    }
}
