package org.example.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.role.LanguageEnum;
import org.example.domain.role.UserRole;
import org.example.dto.BaseEntity;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto implements BaseEntity {
    private String username;
    private String password;
    private UserRole role;
    private LanguageEnum language;
    private Long created_by;
}
