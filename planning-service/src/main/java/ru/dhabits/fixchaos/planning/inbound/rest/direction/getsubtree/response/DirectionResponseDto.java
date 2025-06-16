package ru.dhabits.fixchaos.planning.inbound.rest.direction.getsubtree.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class DirectionResponseDto {
    private UUID id;
    private String code;
    private String name;
    private String description;
    private List<DirectionResponseDto> directions;
}
