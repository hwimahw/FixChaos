package ru.dhabits.fixchaos.planning.usecase.direction.updatedirection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.domain.repository.DirectionRepository;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.planning.usecase.direction.updatedirection.command.UpdateDirectionCommand;
import ru.dhabits.fixchaos.planning.usecase.direction.updatedirection.validator.UpdateDirectionValidatorService;

@Component
@RequiredArgsConstructor
public class UpdateDirectionUseCase {

    private final DirectionRepository directionRepository;
    private final UpdateDirectionValidatorService updateDirectionValidatorService;

    @Transactional
    public Direction execute(UpdateDirectionCommand updateDirectionCommand) {
        updateDirectionValidatorService.validateRequest(updateDirectionCommand);

        Direction direction = directionRepository.findById(updateDirectionCommand.getId()).orElseThrow(
                () -> new EntityAlreadyExistsOrDoesNotExistException(
                        String.format("Направление с id %s не найдена", updateDirectionCommand.getId())
                )
        );
        direction.setCode(updateDirectionCommand.getCode());
        direction.setDescription(updateDirectionCommand.getDescription());
        direction.setName(updateDirectionCommand.getName());
        if (updateDirectionCommand.getParentId() != null) {
            Direction parentDirection = directionRepository.findById(updateDirectionCommand.getParentId()).orElseThrow(
                    () -> new EntityAlreadyExistsOrDoesNotExistException(
                            String.format(
                                    "Родительское направление с id %s не найдена",
                                    updateDirectionCommand.getParentId()
                            )
                    )
            );
            direction.setDirection(parentDirection);
        } else {
            direction.setDirection(null);
        }

        return directionRepository.save(direction);
    }
}

