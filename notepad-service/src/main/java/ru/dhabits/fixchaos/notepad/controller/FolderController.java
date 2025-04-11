package ru.dhabits.fixchaos.notepad.controller;

import com.dhabits.code.fixchaos.notepad.controller.FolderApi;
import com.dhabits.code.fixchaos.notepad.dto.FolderDto;
import com.dhabits.code.fixchaos.notepad.dto.ListFolderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.dhabits.fixchaos.notepad.service.FolderService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FolderController implements FolderApi {

    private final FolderService folderService;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<FolderDto> createFolder(@Valid FolderDto folderDto) {
        String folderDtoString;
        try {
            folderDtoString = objectMapper.writeValueAsString(folderDto);
        } catch (Exception exception) {
            throw new RuntimeException("Ошибка при конвертации folderDto в строку");
        }

        log.info(String.format("Запрос на создание папки %s начался", folderDtoString));

        FolderDto folderDtoResponse = folderService.createFolder(folderDto);

        log.info(String.format("Запрос на создание папки %s завершился", folderDtoString));
        return ResponseEntity.ok(folderDtoResponse);
    }

    @Override
    public ResponseEntity<Void> deleteFolder(String id) {
        log.info(String.format("Запрос на удаление папки с id %s начался", id));

        folderService.deleteFolder(id);

        log.info(String.format("Запрос на удаление папки с id %s завершился", id));
        return ResponseEntity.status(204).build();
    }

    @Override
    public ResponseEntity<ListFolderDto> getAllFolders() {
        log.info("Запрос на получение всех папок начался");

        ListFolderDto listFolderDto = folderService.getAllFolders();

        log.info("Запрос на получение всех папок завершился");
        return ResponseEntity.ok(listFolderDto);
    }

    @Override
    public ResponseEntity<Void> updateFolder(String folderId, @Valid String folderName) {
        log.info(String.format("Запрос на обновление папки с id %s начался", folderId));

        folderService.updateFolder(folderId, folderName);

        log.info(String.format("Запрос на обновление папки с id %s завершился", folderId));
        return ResponseEntity.ok().build();
    }
}
