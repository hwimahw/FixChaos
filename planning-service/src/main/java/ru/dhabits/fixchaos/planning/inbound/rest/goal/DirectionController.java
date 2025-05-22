package ru.dhabits.fixchaos.planning.inbound.rest.goal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.direction.createdirection.mapper.CreateDirectionMapper;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.direction.createdirection.request.DirectionRequestDto;
import ru.dhabits.fixchaos.planning.inbound.rest.goal.direction.createdirection.response.DirectionResponseDto;
import ru.dhabits.fixchaos.planning.usecase.direction.createdirection.CreateDirectionUseCase;
import ru.dhabits.fixchaos.planning.usecase.direction.deletedirection.DeleteDirectionUseCase;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/direction")
public class DirectionController {

    private final CreateDirectionUseCase createDirectionUseCase;
    private final CreateDirectionMapper createDirectionMapper;

    private final DeleteDirectionUseCase deleteDirectionUseCase;

    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DirectionResponseDto> createDirection(@RequestBody DirectionRequestDto directionRequestDto) {
        Direction direction = createDirectionUseCase.execute(
                createDirectionMapper.toDirectionCommand(directionRequestDto)
        );
        return ResponseEntity.ok(createDirectionMapper.toDirectionResponseDto(direction));
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DirectionResponseDto> deleteDirection(@PathVariable UUID id) {
        deleteDirectionUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

}
