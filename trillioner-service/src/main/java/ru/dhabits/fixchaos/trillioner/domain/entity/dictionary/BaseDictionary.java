package ru.dhabits.fixchaos.trillioner.domain.entity.dictionary;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
public class BaseDictionary {
    @Id
    private String code;
    private String name;
}
