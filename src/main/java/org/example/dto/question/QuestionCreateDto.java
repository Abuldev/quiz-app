package org.example.dto.question;

import lombok.*;
import org.example.domain.auth.Answer;
import org.example.domain.role.LanguageEnum;
import org.example.domain.role.QuizLevel;
import org.example.dto.BaseEntity;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionCreateDto implements BaseEntity {
    private Long created_by;
    private String question;
    private Long subject_id;
    private QuizLevel level;
    private LanguageEnum language;
    private List<Answer> answers;
}
