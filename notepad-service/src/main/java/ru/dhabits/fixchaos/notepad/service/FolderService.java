package ru.dhabits.fixchaos.notepad.service;

import com.dhabits.code.fixchaos.notepad.dto.FolderDto;

public interface FolderService {
    FolderDto createFolder(FolderDto folderDto);

    void updateFolder(String folderId, String folderName);
}
