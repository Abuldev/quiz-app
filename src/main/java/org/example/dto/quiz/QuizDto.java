package org.example.dto.quiz;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.domain.auth.QuizQuestion;
import org.example.domain.role.LanguageEnum;
import org.example.domain.role.QuizLevel;
import org.example.dto.GenericEntity;

import java.util.List;

@Getter
@Setter
@ToString
public class QuizDto extends GenericEntity {

   private String quizName;
   private Long subject_id;
   private QuizLevel quizLevel;
   private LanguageEnum languageEnum;
   private Integer quizCount;
   private List<QuizQuestion> quizQuestions;

   @Builder
   public QuizDto(Long id, String quizName, Long subject_id, QuizLevel quizLevel, LanguageEnum languageEnum, Integer quizCount, List<QuizQuestion> quizQuestions) {
      super(id);
      this.quizName = quizName;
      this.subject_id = subject_id;
      this.quizLevel = quizLevel;
      this.languageEnum = languageEnum;
      this.quizCount = quizCount;
      this.quizQuestions = quizQuestions;
   }
}
