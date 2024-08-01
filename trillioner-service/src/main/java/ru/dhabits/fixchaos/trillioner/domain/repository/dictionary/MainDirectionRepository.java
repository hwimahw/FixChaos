package ru.dhabits.fixchaos.trillioner.domain.repository.dictionary;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dhabits.fixchaos.trillioner.domain.entity.dictionary.MainDirection;


public interface MainDirectionRepository extends JpaRepository<MainDirection, String> {
}
