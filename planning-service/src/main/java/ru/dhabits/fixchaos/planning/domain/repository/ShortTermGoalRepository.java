package ru.dhabits.fixchaos.planning.domain.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.dhabits.fixchaos.planning.domain.entity.ShortTermGoal;

import java.util.UUID;

public interface ShortTermGoalRepository extends JpaRepository<ShortTermGoal, UUID>,
        PagingAndSortingRepository<ShortTermGoal, UUID> {

    @NotNull
    Page<ShortTermGoal> findAll(@NotNull Pageable pageable);

}
