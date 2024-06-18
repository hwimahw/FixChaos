package ru.dhabits.fixchaos.notepad.service.impl;

import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import com.dhabits.code.fixchaos.notepad.dto.ListFolderDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dhabits.fixchaos.notepad.db.model.Folder;
import ru.dhabits.fixchaos.notepad.db.repository.FolderRepository;
import ru.dhabits.fixchaos.notepad.error.EntityAlreadyExistsOrDoesNotExistException;
import ru.dhabits.fixchaos.notepad.mapper.FolderMapper;
import ru.dhabits.fixchaos.notepad.service.FolderService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.dhabits.fixchaos.notepad.util.Utils.setFolderToNotebooks;
import static ru.dhabits.fixchaos.notepad.util.Utils.setNotebooksToNotes;

@Service
@RequiredArgsConstructor
@Slf4j
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final FolderMapper folderMapper;

    @Override
    @Transactional
    public FolderDto createFolder(FolderDto folderDto) {
        if (folderRepository.existsByName(folderDto.getName())) {
            throw new EntityAlreadyExistsOrDoesNotExistException("Folder with name " + folderDto.getName() + " already exists");
        }
        Folder folder = folderMapper.mapToFolder(folderDto);
        setFolderToNotebooks(folder, folder.getNotebooks());
        setNotebooksToNotes(folder.getNotebooks());
        return folderMapper.mapToFolderDto(folderRepository.save(folder));
    }

    @Override
    public void updateFolder(String folderId, String name) {
        if (name == null) {
            return;
        }
        Optional<Folder> folderOptional = folderRepository.findById(UUID.fromString(folderId));
        folderOptional.ifPresentOrElse(folder -> {
            folder.setName(name);
            folderRepository.save(folder);
        }, () -> {
            throw new EntityAlreadyExistsOrDoesNotExistException();
        });
    }

    @Override
    public void deleteFolder(String folderId) {
        Optional<Folder> folderOptional = folderRepository.findById(UUID.fromString(folderId));
        folderOptional.ifPresentOrElse(folderRepository::delete, () -> {
            throw new EntityAlreadyExistsOrDoesNotExistException();
        });
    }

    @Override
    public ListFolderDto getAllFolders() {
        List<Folder> folders = folderRepository.findAll();
        return new ListFolderDto().folders(folderMapper.mapToFolderDtoList(folders));
    }
}
