package org.example.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.domain.role.LanguageEnum;
import org.example.domain.role.UserRole;
import org.example.dto.BaseEntity;
import org.example.dto.GenericEntity;

@Getter
@Setter
@ToString
public class UserDto implements BaseEntity {
    public Long id;
    private String username;
    private String password;
    private UserRole role;
    private LanguageEnum language;
    private Integer isActive;

    @Builder
    public UserDto(Long id, String username, String password, UserRole role, LanguageEnum language) {
        this.id=id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.language = language;
    }
}
