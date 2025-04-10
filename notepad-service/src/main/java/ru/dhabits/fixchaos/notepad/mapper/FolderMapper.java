package ru.dhabits.fixchaos.notepad.mapper;

import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.notepad.db.model.Folder;

import java.util.List;

@Mapper(componentModel = "spring", uses = {NotebookMapper.class})
public interface FolderMapper {
    Folder mapToFolder(FolderDto folderDto);

    List<FolderDto> mapToFolderDtoList(List<Folder> folders);

    FolderDto mapToFolderDto(Folder folder);
}
