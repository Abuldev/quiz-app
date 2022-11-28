package org.example.dto.question;

import lombok.Builder;
import lombok.Getter;
import org.example.domain.auth.Answer;
import org.example.domain.auth.Subject;
import org.example.domain.role.LanguageEnum;
import org.example.domain.role.QuizLevel;
import org.example.dto.GenericEntity;

import java.util.List;

/**
 * @author Shoniyazova Matlyuba
 * @project QuizAppTeam
 * @since 22/07/22  13:40 (Friday)
 */
@Getter
public class QuestionDto extends GenericEntity {
    private String question;
    private Subject subject;
    private Long subject_id;
    private QuizLevel level;
    private LanguageEnum language;
    private List<Answer> answers;

    @Builder
    public QuestionDto(Long id, String question, Subject subject, Long subject_id, QuizLevel level, LanguageEnum language, List<Answer> answers) {
        super(id);
        this.question = question;
        this.subject = subject;
        this.subject_id = subject_id;
        this.level = level;
        this.language = language;
        this.answers = answers;
    }
}
