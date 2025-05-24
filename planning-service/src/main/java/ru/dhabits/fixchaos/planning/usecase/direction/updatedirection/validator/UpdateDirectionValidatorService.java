package ru.dhabits.fixchaos.planning.usecase.direction.updatedirection.validator;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.usecase.direction.updatedirection.command.UpdateDirectionCommand;
import ru.dhabits.fixchaos.planning.usecase.exceptions.InvalidRequestException;

@Component
@RequiredArgsConstructor
public class UpdateDirectionValidatorService {

    public void validateRequest(UpdateDirectionCommand updateDirectionCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        if (updateDirectionCommand.getCode() == null) {
            stringBuilder.append("Отсутствует обязательный параметр \"Код направления\"");
            stringBuilder.append(" ");
        }
        if (updateDirectionCommand.getName() == null) {
            stringBuilder.append("Отсутствует обязательный параметр \"Имя направления\"");
        }
        if (!stringBuilder.isEmpty()) {
            throw new InvalidRequestException(stringBuilder.toString());
        }
    }
}
