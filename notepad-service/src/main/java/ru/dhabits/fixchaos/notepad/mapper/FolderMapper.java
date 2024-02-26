package ru.dhabits.fixchaos.notepad.mapper;

import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.notepad.db.model.Folder;

@Mapper(componentModel = "spring")
public interface FolderMapper {
    Folder mapToFolder(FolderDto folderDto);

    FolderDto mapToFolderDto(Folder folder);

}
