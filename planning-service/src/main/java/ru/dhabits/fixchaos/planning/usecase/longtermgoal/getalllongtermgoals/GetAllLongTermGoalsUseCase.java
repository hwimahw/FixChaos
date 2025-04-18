package ru.dhabits.fixchaos.planning.usecase.longtermgoal.getalllongtermgoals;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.domain.entity.LongTermGoal;
import ru.dhabits.fixchaos.planning.domain.repository.LongTermGoalRepository;
import ru.dhabits.fixchaos.planning.usecase.longtermgoal.getalllongtermgoals.mapper.GetAllLongTermGoalsUseCaseMapper;
import ru.dhabits.fixchaos.planning.usecase.longtermgoal.getalllongtermgoals.response.LongTermGoalListResultDto;

@Component
@RequiredArgsConstructor
public class GetAllLongTermGoalsUseCase {

    private final LongTermGoalRepository longTermGoalRepository;
    private final GetAllLongTermGoalsUseCaseMapper getAllLongTermGoalsUseCaseMapper;

    public LongTermGoalListResultDto execute(Pageable pageable) {
        Page<LongTermGoal> page = longTermGoalRepository.findAll(pageable);
        return getAllLongTermGoalsUseCaseMapper.toLongTermGoalListResponseDto(page);
    }
}
