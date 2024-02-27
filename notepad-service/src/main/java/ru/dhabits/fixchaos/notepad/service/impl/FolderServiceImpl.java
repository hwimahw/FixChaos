package ru.dhabits.fixchaos.notepad.service.impl;

import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dhabits.fixchaos.notepad.db.model.Folder;
import ru.dhabits.fixchaos.notepad.db.model.Note;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;
import ru.dhabits.fixchaos.notepad.db.repository.FolderRepository;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.notepad.mapper.FolderMapper;
import ru.dhabits.fixchaos.notepad.service.FolderService;

import java.util.List;

import static ru.dhabits.fixchaos.notepad.util.Utils.setFolderToNotebooks;
import static ru.dhabits.fixchaos.notepad.util.Utils.setNotebooksToNotes;

@Service
@RequiredArgsConstructor
@Slf4j
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final FolderMapper folderMapper;

    @Override
    public FolderDto createFolder(FolderDto folderDto) {
        if (folderRepository.existsByName(folderDto.getName())) {
            throw new EntityAlreadyExistsOrDoesNotExistException("Folder with name " + folderDto.getName() + " already exists");
        }
        Folder folder = folderMapper.mapToFolder(folderDto);
        setFolderToNotebooks(folder, folder.getNotebooks());
        setNotebooksToNotes(folder.getNotebooks());
        return folderMapper.mapToFolderDto(folderRepository.save(folder));
    }
}
