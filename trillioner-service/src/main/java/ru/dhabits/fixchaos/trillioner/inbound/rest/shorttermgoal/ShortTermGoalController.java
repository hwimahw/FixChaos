package ru.dhabits.fixchaos.trillioner.inbound.rest.shorttermgoal;

import com.dhabits.code.fixchaos.trillioner.controller.ShortTermGoalApi;

import com.dhabits.code.fixchaos.trillioner.dto.ShortTermGoalListResponseDto;
import com.dhabits.code.fixchaos.trillioner.dto.ShortTermGoalRequestDto;
import com.dhabits.code.fixchaos.trillioner.dto.ShortTermGoalResponseDto;
import com.dhabits.code.fixchaos.trillioner.dto.UpdateShortTermGoalRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.dhabits.fixchaos.trillioner.inbound.rest.shorttermgoal.createshorttermgoal.mapper.CreateShortTermGoalMapper;
import ru.dhabits.fixchaos.trillioner.inbound.rest.shorttermgoal.getallshorttermgoals.GetAllShortTermGoalsMapper;
import ru.dhabits.fixchaos.trillioner.inbound.rest.shorttermgoal.getshorttermgoal.GetShortTermGoalMapper;
import ru.dhabits.fixchaos.trillioner.inbound.rest.shorttermgoal.updateshorttermgoal.UpdateShortTermGoalMapper;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.createshorttermgoal.CreateShortTermGoalUseCase;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.deleteshorttermgoal.DeleteShortTermGoalUseCase;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.getallshorttermgoals.GetAllShortTermGoalsUseCase;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.getshorttermgoal.GetShortTermGoalUseCase;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.getshorttermgoal.response.ShortTermGoalInfo;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.updateshorttermgoal.UpdateShortTermGoalUseCase;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ShortTermGoalController implements ShortTermGoalApi {

    private final CreateShortTermGoalUseCase createShortTermGoalUseCase;
    private final CreateShortTermGoalMapper createShortTermGoalMapper;

    private final UpdateShortTermGoalUseCase updateShortTermGoalUseCase;
    private final UpdateShortTermGoalMapper updateShortTermGoalMapper;

    private final DeleteShortTermGoalUseCase deleteShortTermGoalUseCase;

    private final GetShortTermGoalUseCase getShortTermGoalUseCase;
    private final GetShortTermGoalMapper getShortTermGoalMapper;

    private final GetAllShortTermGoalsUseCase getAllShortTermGoalsUseCase;
    private final GetAllShortTermGoalsMapper getAllShortTermGoalsMapper;

    @Override
    public ResponseEntity<ShortTermGoalResponseDto> createShortTermGoal(ShortTermGoalRequestDto shortTermGoalRequestDto) {
        var shortTermGoalCommand = createShortTermGoalMapper.toShortTermGoalCommand(shortTermGoalRequestDto);
        var shortTermGoalInfo = createShortTermGoalUseCase.execute(shortTermGoalCommand);
        return ResponseEntity.ok(createShortTermGoalMapper.toShortTermGoalResponseDto(shortTermGoalInfo));
    }

    @Override
    public ResponseEntity<Void> deleteShortTermGoal(UUID id) {
        deleteShortTermGoalUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ShortTermGoalListResponseDto> getAllShortTermGoals(Pageable pageable) {
        return ResponseEntity.ok(
                getAllShortTermGoalsMapper.toShortTermGoalResponseDto(
                        getAllShortTermGoalsUseCase.execute(pageable)
                )
        );
    }

    @Override
    public ResponseEntity<ShortTermGoalResponseDto> getShortTermGoal(UUID id) {
        ShortTermGoalInfo shortTermGoalInfo = getShortTermGoalUseCase.execute(id);
        return ResponseEntity.ok(getShortTermGoalMapper.toShortTermGoalResponseDto(shortTermGoalInfo));
    }

    @Override
    public ResponseEntity<Void> updateShortTermGoal(
            UUID id,
            UpdateShortTermGoalRequestDto updateShortTermGoalRequestDto
    ) {
        var updateShortTermGoalCommand = updateShortTermGoalMapper.toUpdateShortTermGoalCommand(
                updateShortTermGoalRequestDto
        );
        updateShortTermGoalUseCase.execute(id, updateShortTermGoalCommand);
        return ResponseEntity.ok().build();
    }
}
