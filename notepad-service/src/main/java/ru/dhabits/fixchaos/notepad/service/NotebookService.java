package ru.dhabits.fixchaos.notepad.service;

import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;

public interface NotebookService {
    NotebookDto createNotebook(NotebookDto notebookDto);
}
