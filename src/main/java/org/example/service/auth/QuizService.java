package org.example.service.auth;

import org.example.config.ApplicationContextHolder;
import org.example.criteria.auth.QuizCriteria;
import org.example.domain.auth.Quiz;
import org.example.domain.auth.Subject;
import org.example.domain.role.LanguageEnum;
import org.example.domain.role.QuizLevel;
import org.example.dto.quiz.QuizCreateDto;
import org.example.dto.quiz.QuizUpdateDto;
import org.example.repository.auth.QuizRepository;
import org.example.response.Data;
import org.example.response.ResponseEntity;
import org.example.service.Service;
import org.example.service.ServiceCRUD;

import java.util.List;
import java.util.Optional;

public class QuizService implements Service, ServiceCRUD<
        QuizCreateDto, QuizUpdateDto, Long, QuizCriteria, Quiz> {

    private static QuizService instance;
    private QuizRepository quizRepository = ApplicationContextHolder.getBean(QuizRepository.class);


    public static QuizService getInstance() {
        if (instance == null) {
            instance = new QuizService();
        }
        return instance;
    }


    @Override
    public ResponseEntity<Data<Boolean>> create(QuizCreateDto quizCreateDto) {
       try {
           Optional<Boolean> save = quizRepository.save(Quiz.builder()
                   .createdBy(quizCreateDto.getCreated_by())
//                   .quizQuestions(quizCreateDto.getQuizQuestions())
                   .language(quizCreateDto.getLanguageEnum())
                   .level(quizCreateDto.getQuizLevel())
                   .subject_id(quizCreateDto.getSubject_id())
                   .quizName(quizCreateDto.getQuizName())
                   .quizCount(quizCreateDto.getQuizCount())
                   .build());

           if (save.isPresent() && save.get().equals(true)) {
               return new ResponseEntity<>(new Data<>(true));
           }
        } catch (Exception e){
           e.printStackTrace();
       }
       return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .friendlyMessage("failed")
                .developerMessage("failed")
                .code(400)
                .build()));

    }

    @Override
    public ResponseEntity<Data<Boolean>> update(QuizUpdateDto quizUpdateDto) {
        return null;
    }

    @Override
    public ResponseEntity<Data<Quiz>> get(Long aLong) {
        try {
            Optional<Quiz> quiz = quizRepository.get(aLong);
            if (quiz.isPresent()) {
                return new ResponseEntity<>(new Data<>(quiz.get()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .friendlyMessage("failed")
                .developerMessage("failed")
                .code(400)
                .build()));
    }

    @Override
    public ResponseEntity<Data<List<Quiz>>> getAll(QuizCriteria criteria) {
      try {
          Optional<List<Quiz>> all = quizRepository.getAll();
          if (all.isPresent()) {
              return new ResponseEntity<>(new Data<>(all.get()));
          }
      }catch (Exception e){
          e.printStackTrace();
      }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .friendlyMessage("failed")
                .developerMessage("failed")
                .code(400)
                .build()));
    }

    @Override
    public ResponseEntity<Data<Boolean>> delete(Long aLong) {
        try {
            Optional<Boolean> delete = quizRepository.delete(aLong);

            if (delete.isPresent()) {
                if (delete.get().equals(true)) {
                    return new ResponseEntity<>(new Data<>(true));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .friendlyMessage("failed")
                .developerMessage("failed")
                .code(400)
                .build()));
    }
}
