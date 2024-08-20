package ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.getallshorttermgoals;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.trillioner.domain.entity.ShortTermGoal;
import ru.dhabits.fixchaos.trillioner.domain.repository.ShortTermGoalRepository;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.getallshorttermgoals.mapper.GetAllShortTermGoalsUseCaseMapper;
import ru.dhabits.fixchaos.trillioner.usecase.shorttermgoal.getallshorttermgoals.response.ShortTermGoalListResultDto;

@Component
@RequiredArgsConstructor
public class GetAllShortTermGoalsUseCase {

    private final ShortTermGoalRepository shortTermGoalRepository;
    private final GetAllShortTermGoalsUseCaseMapper getAllShortTermGoalsUseCaseMapper;

    public ShortTermGoalListResultDto execute(Pageable pageable) {
        Page<ShortTermGoal> page = shortTermGoalRepository.findAll(pageable);
        return getAllShortTermGoalsUseCaseMapper.toShortTermGoalListResponseDto(page);
    }
}
