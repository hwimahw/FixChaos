package ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.getshorttermgoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.trillioner.domain.entity.LongTermGoal;
import ru.dhabits.fixchaos.trillioner.domain.repository.LongTermGoalRepository;
import ru.dhabits.fixchaos.trillioner.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.getshorttermgoal.mapper.GetLongTermUseCaseMapper;
import ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.getshorttermgoal.response.LongTermGoalInfo;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetLongTermGoalUseCase {

    private final LongTermGoalRepository longTermGoalRepository;
    private final GetLongTermUseCaseMapper getLongTermUseCaseMapper;

    public LongTermGoalInfo execute(UUID id) {
        LongTermGoal longTermGoal = longTermGoalRepository
                .findById(id)
                .orElseThrow(EntityAlreadyExistsOrDoesNotExistException::new);
        return getLongTermUseCaseMapper.toLongTermGoalInfo(longTermGoal);
    }
}
