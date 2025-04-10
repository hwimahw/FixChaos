package ru.dhabits.fixchaos.notepad.service;

import com.dhabits.code.fixchaos.notepad.dto.ListNotebookDto;
import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;

public interface NotebookService {
    NotebookDto createNotebook(NotebookDto notebookDto);

    void updateNotebook(String id, String name);

    void deleteNotebook(String notebookId);

    ListNotebookDto getNotebooksOfFolder(String folderId);
}
