package org.example.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.auth.QuizQuestion;
import org.example.domain.role.LanguageEnum;
import org.example.domain.role.QuizLevel;
import org.example.dto.BaseEntity;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizCreateDto implements BaseEntity {
    private String quizName;
    private Long subject_id;
    private QuizLevel quizLevel;
    private LanguageEnum languageEnum;
    private Integer quizCount;
    private List<QuizQuestion> quizQuestions;
    private Long created_by;
}
