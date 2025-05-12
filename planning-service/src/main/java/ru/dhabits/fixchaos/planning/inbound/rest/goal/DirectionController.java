package ru.dhabits.fixchaos.planning.inbound.rest.goal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.createdirection.mapper.CreateDirectionMapper;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.createdirection.request.DirectionRequestDto;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.createdirection.response.DirectionResponseDto;
import ru.dhabits.fixchaos.planning.usecase.direction.createdirection.CreateDirectionUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/direction")
public class DirectionController {

    private final CreateDirectionUseCase createDirectionUseCase;
    private final CreateDirectionMapper createDirectionMapper;

    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DirectionResponseDto> createDirection(@RequestBody DirectionRequestDto directionRequestDto) {
        Direction direction = createDirectionUseCase.execute(
                createDirectionMapper.toDirectionCommand(directionRequestDto)
        );
        return ResponseEntity.ok(createDirectionMapper.toDirectionResponseDto(direction));
    }

}
