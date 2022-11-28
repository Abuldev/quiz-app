package org.example.domain.auth;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.Domain;
import org.example.domain.role.LanguageEnum;
import org.example.domain.role.QuizLevel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;
import org.hibernate.type.NumericBooleanConverter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
@Where(clause = "deleted = 0")
public class Quiz implements Domain {
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

    @Column(nullable = false, unique = true)
    private String quizName;

    @Column(nullable = false)
    private Long subject_id;

    @Builder.Default
    @Column(nullable = false)
    @Convert(converter = LevelStringConverter.class)
    private QuizLevel level = QuizLevel.EASY;


    @Column(nullable = false)
    @Convert(converter = LanguageEnumConvertor.class)
    private LanguageEnum language = LanguageEnum.UZ;

    @Builder.Default
    @Convert(converter = NumericBooleanConverter.class)
    private Boolean isCompleted = false;

//    @Builder.Default
    @Column(columnDefinition = "smallint default 0")
    private int quizCount;

//    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = QuizQuestion.class)
//    private List<QuizQuestion> quizQuestions;

//    @Builder.Default
    @Column(columnDefinition = "smallint default 0")
    private int ball;


    static class LevelStringConverter implements AttributeConverter<QuizLevel, String> {
        @Override
        public String convertToDatabaseColumn(QuizLevel quizLevel) {
            return switch (quizLevel) {
                case EASY -> "easy";
                case MEDIUM -> "medium";
                case HARD -> "hard";
            };
        }

        @Override
        public QuizLevel convertToEntityAttribute(String s) {
            return switch (s) {
                case "easy" -> QuizLevel.EASY;
                case "medium" -> QuizLevel.MEDIUM;
                case "hard" -> QuizLevel.HARD;
                default -> null;
            };
        }
    }

    static class LanguageEnumConvertor implements AttributeConverter<LanguageEnum, String> {
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
