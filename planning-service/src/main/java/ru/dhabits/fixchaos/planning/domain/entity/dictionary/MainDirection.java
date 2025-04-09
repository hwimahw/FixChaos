package ru.dhabits.fixchaos.planning.domain.entity.dictionary;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "MAIN_DIRECTIONS", schema = "planning")
@Getter
@Setter
@Immutable
@Accessors(chain = true)
public class MainDirection extends BaseDictionary{

}
