package org.example.dto.auth;


import lombok.*;
import org.example.dto.BaseEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginDto implements BaseEntity {
    private String username;
    private String password;
}
