package ru.dhabits.fixchaos.notepad.service.impl;

import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dhabits.fixchaos.notepad.db.model.Folder;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;
import ru.dhabits.fixchaos.notepad.db.repository.FolderRepository;
import ru.dhabits.fixchaos.notepad.db.repository.NotebookRepository;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.notepad.mapper.NotebookMapper;
import ru.dhabits.fixchaos.notepad.service.NotebookService;
import ru.dhabits.fixchaos.notepad.util.Utils;

import java.util.Optional;

import static ru.dhabits.fixchaos.notepad.util.Utils.setNotebookToNotes;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotebookServiceImpl implements NotebookService {

    private final FolderRepository folderRepository;
    private final NotebookMapper notebookMapper;
    private final NotebookRepository notebookRepository;

    @Override
    public NotebookDto createNotebook(NotebookDto notebookDto) {
        Optional<Folder> folderOptional = folderRepository.findById(notebookDto.getFolderId());
        if (folderOptional.isEmpty()) {
            throw new EntityAlreadyExistsOrDoesNotExistException("Folder with id " + notebookDto.getFolderId() + " does not exist");
        }
        Folder folder = folderOptional.get();
        Notebook notebook = notebookMapper.mapToNotebook(notebookDto);
        setNotebookToNotes(notebook, notebook.getNotes());
        notebook.setFolder(folder);
        return notebookMapper.mapToNotebookDto(notebookRepository.save(notebook));
    }
}
