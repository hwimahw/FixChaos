package ru.dhabits.fixchaos.notepad.repository;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.dhabits.fixchaos.notepad.db.model.Folder;
import ru.dhabits.fixchaos.notepad.db.repository.FolderRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FolderRepositoryTest {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void initFolder() {
        Folder folder = createFolder();
        testEntityManager.persist(folder);
    }

    @Test
    public void existsByName_SuccessfulCheck() {
        Assertions.assertEquals(true, folderRepository.existsByName("folder1"));
    }

    @Test
    public void doesNotExistByName_SuccessfulCheck() {
        Assertions.assertEquals(false, folderRepository.existsByName("folder2"));
    }

    private Folder createFolder() {
        Folder folder = new Folder();
        folder.setName("folder1");
        return folder;
    }

}
