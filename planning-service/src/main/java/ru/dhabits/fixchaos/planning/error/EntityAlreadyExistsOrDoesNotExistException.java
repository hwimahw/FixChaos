package ru.dhabits.fixchaos.planning.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityAlreadyExistsOrDoesNotExistException extends RuntimeException {
    public EntityAlreadyExistsOrDoesNotExistException(String message) {
        super(message);
    }
}
