package org.example.domain.auth;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.Domain;
import org.example.domain.role.LanguageEnum;
import org.example.domain.role.UserRole;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;
import org.hibernate.type.NumericBooleanConverter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "auth_user")
@Where(clause = "deleted = 0 and is_active = 0")
public class AuthUser implements Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false, name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;


    @Builder.Default
    @Convert(converter = NumericBooleanConverter.class)
    private boolean deleted = false;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Builder.Default
    @Column(nullable = false)
    @Convert(converter = UserRoleConverter.class)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;
    @Builder.Default
    @Column(nullable = false)
    @Convert(converter = LanguageEnumConvertor.class)
    private LanguageEnum language = LanguageEnum.UZ;

//    @Builder.Default
    @Column(name = "is_active",columnDefinition = "smallint default 1")
    private int isActive;


//    @Builder.Default
    @Column(columnDefinition = "smallint default 0")
    private int tryCount;


    private static class UserRoleConverter implements AttributeConverter<UserRole, String> {

        @Override
        public String convertToDatabaseColumn(UserRole role) {
            return switch (role) {
                case USER -> "user";
                case ADMIN -> "admin";
                case TEACHER -> "teacher";
            };
        }

        @Override
        public UserRole convertToEntityAttribute(String role) {

            return switch (role) {
                case "user" -> UserRole.USER;
                case "admin" -> UserRole.ADMIN;
                case "teacher" -> UserRole.TEACHER;
                default -> null;
            };
        }
    }

    private static class LanguageEnumConvertor implements AttributeConverter<LanguageEnum, String> {
        @Override
        public String convertToDatabaseColumn(LanguageEnum languageEnum) {
            return switch (languageEnum) {
                case UZ -> "uzbek";
                case RU -> "russian";
                case EN -> "english";
            };
        }

        @Override
        public LanguageEnum convertToEntityAttribute(String language) {
            return switch (language) {
                case "uzbek" -> LanguageEnum.UZ;
                case "russian" -> LanguageEnum.RU;
                case "english" -> LanguageEnum.EN;
                default -> null;
            };
        }
    }
}