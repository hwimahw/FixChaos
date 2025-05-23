package ru.dhabits.fixchaos.planning.error.controller;

import com.dhabits.code.fixchaos.planning.dto.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.dhabits.fixchaos.planning.error.EntityAlreadyExistsOrDoesNotExistException;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionAdviceController {

    @ExceptionHandler(value = {EntityAlreadyExistsOrDoesNotExistException.class, RuntimeException.class})
    public ResponseEntity<ErrorResponseDto> handleException(EntityAlreadyExistsOrDoesNotExistException e) {
        log.error(e.getMessage(), e);

        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
