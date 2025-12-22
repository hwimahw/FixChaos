package ru.dhabits.fixchaos.planning.usecase.goal.deletegoal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.domain.repository.GoalRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteGoalUseCase {

    private final GoalRepository goalRepository;

    public void execute(UUID id) {
        goalRepository.deleteById(id);
    }
}
