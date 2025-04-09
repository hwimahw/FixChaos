package ru.dhabits.fixchaos.planning.domain.repository.dictionary;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dhabits.fixchaos.planning.domain.entity.dictionary.MainDirection;


public interface MainDirectionRepository extends JpaRepository<MainDirection, String> {
}
