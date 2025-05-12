package ru.dhabits.fixchaos.planning.usecase.direction.createdirection.command;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class DirectionCommand {
    private String code;
    private String name;
    private UUID parentId;
}
