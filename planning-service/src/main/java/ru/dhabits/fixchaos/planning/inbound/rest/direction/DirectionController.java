package ru.dhabits.fixchaos.planning.inbound.rest.direction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dhabits.fixchaos.planning.domain.entity.Direction;
import ru.dhabits.fixchaos.planning.inbound.rest.direction.createdirection.mapper.CreateDirectionMapper;
import ru.dhabits.fixchaos.planning.inbound.rest.direction.createdirection.request.CreateDirectionRequestDto;
import ru.dhabits.fixchaos.planning.inbound.rest.direction.createdirection.response.CreateDirectionResponseDto;
import ru.dhabits.fixchaos.planning.inbound.rest.direction.getsubtree.mapper.GetSubtreeOfDirectionMapper;
import ru.dhabits.fixchaos.planning.inbound.rest.direction.getsubtree.response.DirectionResponseDto;
import ru.dhabits.fixchaos.planning.inbound.rest.direction.updatedirection.mapper.UpdateDirectionMapper;
import ru.dhabits.fixchaos.planning.inbound.rest.direction.updatedirection.request.UpdateDirectionRequestDto;
import ru.dhabits.fixchaos.planning.inbound.rest.direction.updatedirection.response.UpdateDirectionResponseDto;
import ru.dhabits.fixchaos.planning.usecase.direction.createdirection.CreateDirectionUseCase;
import ru.dhabits.fixchaos.planning.usecase.direction.deletedirection.DeleteDirectionUseCase;
import ru.dhabits.fixchaos.planning.usecase.direction.getalltrees.GetAllTreesOfDirectionsUseCase;
import ru.dhabits.fixchaos.planning.usecase.direction.getsubtree.GetSubtreeOfDirectionUseCase;
import ru.dhabits.fixchaos.planning.usecase.direction.getsubtree.result.DirectionResultDto;
import ru.dhabits.fixchaos.planning.usecase.direction.updatedirection.UpdateDirectionUseCase;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/direction")
public class DirectionController {

    private final CreateDirectionUseCase createDirectionUseCase;
    private final CreateDirectionMapper createDirectionMapper;

    private final DeleteDirectionUseCase deleteDirectionUseCase;

    private final UpdateDirectionUseCase updateDirectionUseCase;
    private final UpdateDirectionMapper updateDirectionMapper;

    private final GetSubtreeOfDirectionUseCase getSubtreeOfDirectionUseCase;
    private final GetSubtreeOfDirectionMapper getSubtreeOfDirectionMapper;

    private final GetAllTreesOfDirectionsUseCase getAllTreesOfDirectionsUseCase;


    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CreateDirectionResponseDto> createDirection(
            @RequestBody CreateDirectionRequestDto directionRequestDto
    ) {
        Direction direction = createDirectionUseCase.execute(
                createDirectionMapper.toDirectionCommand(directionRequestDto)
        );
        return ResponseEntity.ok(createDirectionMapper.toDirectionResponseDto(direction));
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CreateDirectionResponseDto> deleteDirection(@PathVariable UUID id) {
        deleteDirectionUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UpdateDirectionResponseDto> updateDirection(
            @PathVariable("id") UUID id,
            @RequestBody UpdateDirectionRequestDto updateDirectionRequestDto
    ) {

        Direction direction = updateDirectionUseCase.execute(
                updateDirectionMapper.toUpdateDirectionCommand(id, updateDirectionRequestDto)
        );

        return ResponseEntity.ok(updateDirectionMapper.toUpdateDirectionResponseDto(direction));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DirectionResponseDto> getSubtree(@PathVariable("id") UUID id) {
        DirectionResultDto directionResultDto = getSubtreeOfDirectionUseCase.getSubtreeOfNode(id);
        return ResponseEntity.ok(getSubtreeOfDirectionMapper.toDirectionResponseDto(directionResultDto));
    }

    @GetMapping(value = "/alltrees", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<DirectionResponseDto>> getAllTrees() {
        List<DirectionResultDto> directionResultDtos = getAllTreesOfDirectionsUseCase.getAllTrees();
        return ResponseEntity.ok(getSubtreeOfDirectionMapper.toDirectionResponseDtos(directionResultDtos));
    }
}
