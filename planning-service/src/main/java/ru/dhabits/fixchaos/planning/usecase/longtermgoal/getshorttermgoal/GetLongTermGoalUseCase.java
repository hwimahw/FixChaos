package ru.dhabits.fixchaos.planning.usecase.longtermgoal.getshorttermgoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.domain.entity.LongTermGoal;
import ru.dhabits.fixchaos.planning.domain.repository.LongTermGoalRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.usecase.longtermgoal.getshorttermgoal.mapper.GetLongTermUseCaseMapper;
import ru.dhabits.fixchaos.planning.usecase.longtermgoal.getshorttermgoal.response.LongTermGoalInfo;

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
