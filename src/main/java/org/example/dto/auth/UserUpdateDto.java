package org.example.dto.auth;

import lombok.Builder;
import lombok.Getter;
import org.example.domain.role.LanguageEnum;
import org.example.dto.GenericEntity;

@Getter
public class UserUpdateDto extends GenericEntity {
    private String username;
    private String password;
    private LanguageEnum language;

    @Builder(builderMethodName = "childBuilder")

    public UserUpdateDto(Long id, String username, String password, LanguageEnum language) {
        super(id);
        this.username = username;
        this.password = password;
        this.language = language;
    }
}
