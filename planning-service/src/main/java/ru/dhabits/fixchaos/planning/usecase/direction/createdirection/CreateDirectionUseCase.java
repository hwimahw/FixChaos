package ru.dhabits.fixchaos.planning.usecase.direction.createdirection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.domain.repository.DirectionRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.usecase.direction.createdirection.command.DirectionCommand;

@Component
@RequiredArgsConstructor
public class CreateDirectionUseCase {

    private final DirectionRepository directionRepository;

    public Direction execute(DirectionCommand directionCommand) {
        Direction parentDirection = null;
        if (directionCommand.getParentId() != null) {
            parentDirection = directionRepository.findById(directionCommand.getParentId())
                    .orElseThrow(
                            () -> {
                                throw new EntityAlreadyExistsOrDoesNotExistException("Такой папки не существует");
                            }
                    );
        }
        Direction direction = new Direction()
                .setDirection(parentDirection)
                .setDescription(directionCommand.getDescription())
                .setCode(directionCommand.getCode())
                .setName(directionCommand.getName());
        return directionRepository.save(direction);
    }
}
