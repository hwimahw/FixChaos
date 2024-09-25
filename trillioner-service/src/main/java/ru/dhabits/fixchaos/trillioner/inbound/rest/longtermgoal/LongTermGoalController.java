package ru.dhabits.fixchaos.trillioner.inbound.rest.longtermgoal;

import com.dhabits.code.fixchaos.trillioner.controller.LongTermGoalApi;
import com.dhabits.code.fixchaos.trillioner.dto.LongTermGoalListResponseDto;
import com.dhabits.code.fixchaos.trillioner.dto.LongTermGoalRequestDto;
import com.dhabits.code.fixchaos.trillioner.dto.LongTermGoalResponseDto;
import com.dhabits.code.fixchaos.trillioner.dto.UpdateLongTermGoalRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.dhabits.fixchaos.trillioner.inbound.rest.longtermgoal.createlongtermgoal.mapper.CreateLongTermGoalMapper;
import ru.dhabits.fixchaos.trillioner.inbound.rest.longtermgoal.getalllongtermgoals.GetAllLongTermGoalsMapper;
import ru.dhabits.fixchaos.trillioner.inbound.rest.longtermgoal.getlongtermgoal.GetLongTermGoalMapper;
import ru.dhabits.fixchaos.trillioner.inbound.rest.longtermgoal.updatelongtermgoal.UpdateLongTermGoalMapper;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.createlongtermgoal.CreateLongTermGoalUseCase;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.deletelongtermgoal.DeleteLongTermGoalUseCase;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.getalllongtermgoals.GetAllLongTermGoalsUseCase;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.getshorttermgoal.GetLongTermGoalUseCase;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.getshorttermgoal.response.LongTermGoalInfo;
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

    private final GetLongTermGoalUseCase getLongTermGoalUseCase;
    private final GetLongTermGoalMapper getLongTermGoalMapper;

    private final GetAllLongTermGoalsUseCase getAllLongTermGoalsUseCase;
    private final GetAllLongTermGoalsMapper getAllLongTermGoalsMapper;


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
    public ResponseEntity<LongTermGoalListResponseDto> getAllLongTermGoals(@Valid Pageable pageable) {
        return ResponseEntity.ok(
                getAllLongTermGoalsMapper.toShortTermGoalResponseDto(
                        getAllLongTermGoalsUseCase.execute(pageable)
                )
        );
    }

    @Override
    public ResponseEntity<LongTermGoalResponseDto> getLongTermGoal(UUID id) {
        LongTermGoalInfo longTermGoalInfo = getLongTermGoalUseCase.execute(id);
        return ResponseEntity.ok(getLongTermGoalMapper.toLongTermGoalResponseDto(longTermGoalInfo));
    }

    @Override
    public ResponseEntity<Void> updateLongTermGoal(UUID id, UpdateLongTermGoalRequestDto updateLongTermGoalRequestDto) {
        var updateLongTermGoalCommand = updateLongTermGoalMapper.toUpdateShortTermGoalCommand(updateLongTermGoalRequestDto);
        updateLongTermGoalUseCase.execute(id, updateLongTermGoalCommand);
        return ResponseEntity.ok().build();

    }
}
