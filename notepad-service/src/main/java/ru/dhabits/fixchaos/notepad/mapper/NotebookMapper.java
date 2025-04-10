package ru.dhabits.fixchaos.notepad.mapper;

import com.dhabits.code.fixchaos.notepad.dto.NotebookDto;
import org.mapstruct.Mapper;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotebookMapper {
    Notebook mapToNotebook(NotebookDto notebookDto);

    NotebookDto mapToNotebookDto(Notebook notebook);

    List<NotebookDto> mapToNotebookDtoList(List<Notebook> notebooks);
}
