package ru.dhabits.fixchaos.notepad.service;

import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import com.dhabits.code.fixchaos.notepad.dto.ListFolderDto;

public interface FolderService {
    FolderDto createFolder(FolderDto folderDto);

    void updateFolder(String folderId, String folderName);

    void deleteFolder(String folderId);

    ListFolderDto getAllFolders();
}
