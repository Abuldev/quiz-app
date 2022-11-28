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
@Builder
@ToString
@Entity
@Where(clause = "deleted = 0")
public class Question implements Domain {
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


    @Column(name = "subject_id_")
    private Long subject_id;

    @Builder.Default
    @Convert(converter = NumericBooleanConverter.class)
    private boolean deleted = false;

    @Column(nullable = false)
    private String question;

    @ManyToOne
    private Subject subject;

    @Builder.Default
    @Column(nullable = false)
    @Convert(converter = Quiz.LevelStringConverter.class)
    private QuizLevel level = QuizLevel.EASY;

    @Builder.Default
    @Column(nullable = false)
    @Convert(converter = Quiz.LanguageEnumConvertor.class)
    private LanguageEnum language = LanguageEnum.UZ;

//    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
//    private List<Answer> answers;
}
