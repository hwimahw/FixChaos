package ru.dhabits.fixchaos.planning.usecase.direction.deletedirection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.domain.repository.DirectionRepository;
import ru.dhabits.fixchaos.planning.domain.repository.GoalRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteDirectionUseCase {

    private final DirectionRepository directionRepository;
    private final GoalRepository goalRepository;

    public void execute(UUID id) {
        Direction direction = directionRepository.findById(id).orElseThrow(
                () -> new EntityAlreadyExistsOrDoesNotExistException(
                        String.format("Направление с id %s не найдена", id)
                )
        );
        directionRepository.delete(direction);
    }
}
