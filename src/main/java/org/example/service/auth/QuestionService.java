package org.example.service.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.config.ApplicationContextHolder;
import org.example.criteria.auth.QuestionCriteria;
import org.example.domain.auth.Answer;
import org.example.domain.auth.Question;
import org.example.dto.question.QuestionCreateDto;
import org.example.dto.question.QuestionUpdateDto;
import org.example.dto.quiz.QuizCreateDto;
import org.example.dto.quiz.QuizUpdateDto;
import org.example.repository.auth.AnswerRepository;
import org.example.repository.auth.QuestionRepository;
import org.example.response.Data;
import org.example.response.ResponseEntity;
import org.example.service.Service;
import org.example.service.ServiceCRUD;

import java.util.List;
import java.util.Optional;

/**
 * @author Shoniyazova Matlyuba
 * @project QuizAppTeam
 * @since 22/07/22  13:48 (Friday)
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionService implements Service, ServiceCRUD<QuestionCreateDto, QuestionUpdateDto, Long, QuestionCriteria, Question> {
    private static QuestionService instance;

    private QuestionRepository questionRepository = ApplicationContextHolder.getBean(QuestionRepository.class);
    private AnswerRepository answerRepository = ApplicationContextHolder.getBean(AnswerRepository.class);

    public static QuestionService getInstance() {
        if (instance == null) {
            instance = new QuestionService();
        }
        return instance;
    }

    @Override
    public ResponseEntity<Data<Boolean>> create(QuestionCreateDto questionCreateDto) {
        try {

            List<Answer> answers = questionCreateDto.getAnswers();
            Optional<Boolean> save = questionRepository.save(Question.builder()
                    .subject_id(questionCreateDto.getSubject_id())
                    .question(questionCreateDto.getQuestion())
                    .createdBy(questionCreateDto.getCreated_by())
                    .level(questionCreateDto.getLevel())
                    .language(questionCreateDto.getLanguage())
//                    .answers(answers)
                    .build());

            Question last = questionRepository.getLast();

            if (save.isPresent()) {
                if (save.get().equals(true)) {
                    for (Answer answer : answers) {
                        answer.setQuestion_id(last.getId());
                        answer.setQuestion(last);
                        Optional<Boolean> save1 = answerRepository.save(answer);
                        if (save1.isEmpty()) {
                            throw new RuntimeException();
                        }
                    }
                    return new ResponseEntity<>(new Data<>(true));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .friendlyMessage("failed")
                .developerMessage("failed")
                .code(404)
                .build()));
    }

    @Override
    public ResponseEntity<Data<Boolean>> update(QuestionUpdateDto questionUpdateDto) {
        try {

            ResponseEntity<Data<Question>> dataResponseEntity = get(questionUpdateDto.id);
            if (dataResponseEntity.getData().getIsOK().equals(true)) {
                Question body = dataResponseEntity.getData().getBody();
                body.setQuestion(questionUpdateDto.getQuestion());
                body.setSubject_id(questionUpdateDto.getSubject_id());
                body.setLevel(questionUpdateDto.getLevel());
                body.setLanguage(questionUpdateDto.getLanguage());
                Optional<Boolean> update = questionRepository.update(body);

                if (update.isPresent()) {
                    if (update.get().equals(true)) {
                        return new ResponseEntity<>(new Data<>(true));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .friendlyMessage("failed")
                .developerMessage("failed")
                .code(404)
                .build()));
    }

    @Override
    public ResponseEntity<Data<Question>> get(Long aLong) {
        try {
            Optional<Question> question = questionRepository.get(aLong);

            if (question.isPresent()) {
                return new ResponseEntity<>(new Data<>(question.get()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .friendlyMessage("failed")
                .developerMessage("failed")
                .code(404)
                .build()));
    }

    @Override
    public ResponseEntity<Data<List<Question>>> getAll(QuestionCriteria criteria) {
        try {
            Optional<List<Question>> all = questionRepository.getAll();
            if (all.isPresent()) {
                return new ResponseEntity<>(new Data<>(all.get()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .friendlyMessage("failed")
                .developerMessage("failed")
                .code(404)
                .build()));
    }

    @Override
    public ResponseEntity<Data<Boolean>> delete(Long aLong) {
        try {
            Optional<Boolean> delete = questionRepository.delete(aLong);

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
                .code(404)
                .build()));
    }
}
