package org.example.domain.role;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LanguageEnum {
    UZ("O'zbek", "Узбек", "Uzbek"),
    RU("Rus", "Русский", "Russian"),
    EN("Ingliz", "Английский", "English");

    private final String uz;
    private final String ru;
    private final String en;
}
