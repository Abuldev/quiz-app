package org.example.dto.question;

import lombok.Builder;
import lombok.Getter;
import org.example.domain.auth.Answer;
import org.example.domain.role.LanguageEnum;
import org.example.domain.role.QuizLevel;
import org.example.dto.GenericEntity;

import java.util.List;


@Getter
public class QuestionUpdateDto extends GenericEntity {
    private String question;
    private Long subject_id;
    private QuizLevel level;
    private LanguageEnum language;
    private List<Answer> answers;


    @Builder
    public QuestionUpdateDto(Long id, String question, Long subject_id, QuizLevel level, LanguageEnum language, List<Answer> answers) {
        super(id);
        this.question = question;
        this.subject_id = subject_id;
        this.level = level;
        this.language = language;
        this.answers = answers;
    }
}
