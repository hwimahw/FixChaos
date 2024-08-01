package ru.dhabits.fixchaos.trillioner.inbound.rest.shorttermgoal;

import com.dhabits.code.fixchaos.trillioner.controller.ShortTermGoalApi;
import com.dhabits.code.fixchaos.trillioner.dto.ShortTermGoalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.dhabits.fixchaos.trillioner.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.trillioner.domain.repository.dictionary.MainDirectionRepository;
import ru.dhabits.fixchaos.trillioner.service.DictionaryService;

@Controller
@RequiredArgsConstructor
public class ShortTermGoalController implements ShortTermGoalApi {
    DictionaryService dictionaryService;
    MainDirectionRepository mainDirectionRepository;

    @Override
    public ResponseEntity<ShortTermGoalDto> createShortTermGoal(ShortTermGoalDto shortTermGoalDto) {
        MainDirection mainDirection = dictionaryService.getEntity(
                shortTermGoalDto.getMainDirection(),
                MainDirection.class,
                mainDirectionRepository
        );
        return null;
    }
}
