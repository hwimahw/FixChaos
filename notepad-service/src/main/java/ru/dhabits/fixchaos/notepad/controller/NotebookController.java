package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.controller.NotebookApi;
import com.dhabits.code.fixchaos.notepad.dto.ListNotebookDto;
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

    @Override
    public ResponseEntity<Void> deleteNotebook(String id) {
        notebookService.deleteNotebook(id);
        return ResponseEntity.status(204).build();
    }

    @Override
    public ResponseEntity<ListNotebookDto> getNotebooksOfFolder(String folderId) {
        return ResponseEntity.ok(notebookService.getNotebooksOfFolder(folderId));
    }

    @Override
    public ResponseEntity<Void> updateNotebook(String id, @Valid String name) {
        notebookService.updateNotebook(id, name);
        return ResponseEntity.ok().build();

    }
}
