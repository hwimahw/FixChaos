package ru.dhabits.fixchaos.planning.usecase.direction.createdirection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.domain.repository.DirectionRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.usecase.direction.createdirection.command.CreateDirectionCommand;

@Component
@RequiredArgsConstructor
public class CreateDirectionUseCase {

    private final DirectionRepository directionRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Direction execute(CreateDirectionCommand directionCommand) {
        Direction parentDirection = null;
        if (directionCommand.getParentId() != null) {
            parentDirection = directionRepository.findById(directionCommand.getParentId())
                    .orElseThrow(
                            () -> {
                                throw new EntityAlreadyExistsOrDoesNotExistException(
                                        "Такого родительского направления не существует"
                                );
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
