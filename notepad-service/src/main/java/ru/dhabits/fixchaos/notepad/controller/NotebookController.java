package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.controller.NotebookApi;
import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.dhabits.fixchaos.notepad.service.NotebookService;

@Controller
@RequiredArgsConstructor
public class NotebookController implements NotebookApi {
    private final NotebookService notebookService;

    @Override
    public ResponseEntity<NotebookDto> createNotebook(@Valid NotebookDto notebookDto) {
        return ResponseEntity.ok(notebookService.createNotebook(notebookDto));
    }
}
