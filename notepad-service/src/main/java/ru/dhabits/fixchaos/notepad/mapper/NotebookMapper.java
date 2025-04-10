package ru.dhabits.fixchaos.notepad.mapper;

import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;

import java.util.List;

@Mapper(componentModel = "spring", uses = {NoteMapper.class})
public interface NotebookMapper {
    Notebook mapToNotebook(NotebookDto notebookDto);

    @Mapping(target = "folderId", source = "folder.id")
    NotebookDto mapToNotebookDto(Notebook notebook);

    List<NotebookDto> mapToNotebookDtoList(List<Notebook> notebooks);
}
