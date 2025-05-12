package ru.dhabits.fixchaos.planning.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dhabits.fixchaos.planning.domain.entity.dictionary.Instrument;

import java.util.UUID;


public interface InstrumentRepository extends JpaRepository<Instrument, UUID> {
}
