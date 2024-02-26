package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.controller.FolderApi;
import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.dhabits.fixchaos.notepad.service.FolderService;

@Controller
@RequiredArgsConstructor
public class FolderController implements FolderApi {

    private final FolderService folderService;

    @Override
    public ResponseEntity<FolderDto> createFolder(@Valid FolderDto folderDto) {
        return ResponseEntity.ok(folderService.createFolder(folderDto));
    }
}
