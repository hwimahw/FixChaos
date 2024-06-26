package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.controller.FolderApi;
import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import com.dhabits.code.fixchaos.notepad.dto.ListFolderDto;
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

    @Override
    public ResponseEntity<Void> deleteFolder(String id) {
        folderService.deleteFolder(id);
        return ResponseEntity.status(204).build();
    }

    @Override
    public ResponseEntity<ListFolderDto> getAllFolders() {
        return ResponseEntity.ok(folderService.getAllFolders());
    }

    @Override
    public ResponseEntity<Void> updateFolder(String folderId, @Valid String folderName) {
        folderService.updateFolder(folderId, folderName);
        return ResponseEntity.ok().build();
    }
}
