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

import java.util.Optional;
import java.util.UUID;

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
        Notebook notebook = notebookMapper.mapToNotebook(notebookDto).setFolder(folderOptional.get());
        setNotebookToNotes(notebook, notebook.getNotes());
        return notebookMapper.mapToNotebookDto(notebookRepository.save(notebook));
    }

    @Override
    public void updateNotebook(String id, String name) {
        if (name == null) {
            return;
        }
        notebookRepository.findById(UUID.fromString(id)).ifPresentOrElse(
                notebook -> {
                    notebook.setName(name);
                    notebookRepository.save(notebook);
                }, () -> {
                    throw new EntityAlreadyExistsOrDoesNotExistException();
                }
        );
    }
}
