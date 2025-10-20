package ru.dhabits.fixchaos.planning.usecase.goal.updategoal.validator;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dhabits.fixchaos.planning.usecase.exceptions.InvalidRequestException;
import ru.dhabits.fixchaos.planning.usecase.goal.updategoal.command.UpdateGoalCommand;

@Component
@RequiredArgsConstructor
public class UpdateGoalValidatorService {

    public void validateRequest(UpdateGoalCommand updateDirectionCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        if (updateDirectionCommand.getDirectionId() == null) {
            stringBuilder.append("Отсутствует обязательный параметр \"Идентификатор направления\"");
            stringBuilder.append(" ");
        }
        if (updateDirectionCommand.getName() == null) {
            stringBuilder.append("Отсутствует обязательный параметр \"Имя цели\"");
        }
        if (updateDirectionCommand.getStartDate() == null) {
            stringBuilder.append("Отсутствует обязательный параметр \"Начальная дата цели\"");
        }
        if (updateDirectionCommand.getEndDate() == null) {
            stringBuilder.append("Отсутствует обязательный параметр \"Конечная дата цели\"");
        }
        if (updateDirectionCommand.getGoalType() == null) {
            stringBuilder.append("Отсутствует обязательный параметр \"Тип цели\"");
        }
        if (!stringBuilder.isEmpty()) {
            throw new InvalidRequestException(stringBuilder.toString());
        }
    }
}
