package org.example.service.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.config.ApplicationContextHolder;
import org.example.criteria.auth.AnswerCriteria;
import org.example.domain.auth.Answer;
import org.example.dto.answer.AnswerCreateDto;
import org.example.dto.answer.AnswerUpdateDto;
import org.example.repository.auth.AnswerRepository;
import org.example.response.Data;
import org.example.response.ResponseEntity;
import org.example.service.Service;
import org.example.service.ServiceCRUD;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerService implements Service, ServiceCRUD<
        AnswerCreateDto, AnswerUpdateDto, Long, AnswerCriteria, Answer> {

    private AnswerRepository answerRepository = ApplicationContextHolder.getBean(AnswerRepository.class);
    private static AnswerService instance;

    public static AnswerService getInstance() {
        if (instance == null) {
            instance = new AnswerService();
        }
        return instance;
    }

    @Override
    public ResponseEntity<Data<Boolean>> create(AnswerCreateDto answerCreateDto) {
        try {

                    Optional<Boolean> save = answerRepository.save(Answer.builder()
                            .answer(answerCreateDto.getAnswer())
                            .createdBy(answerCreateDto.getCreated_by())
                            .question_id(answerCreateDto.getQuestion_id())
                            .isTrue(answerCreateDto.getIsTrue())
                            .build());

                    if (save.isPresent()) {
                        if (save.get().equals(true)) {
                            return new ResponseEntity<>(new Data<>(true));
                        }
                    }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .friendlyMessage("failed")
                .code(404)
                .build()));
    }

    @Override
    public ResponseEntity<Data<Boolean>> update(AnswerUpdateDto answerUpdateDto) {
        try {
                    ResponseEntity<Data<Answer>> dataResponseEntity = get(answerUpdateDto.getId());
                    if (dataResponseEntity.getData().getIsOK().equals(true)) {
                        Answer body = dataResponseEntity.getData().getBody();
                        body.setAnswer(answerUpdateDto.getAnswer());
                        body.setIsTrue(answerUpdateDto.getIsTrue());
                        body.setUpdatedBy(answerUpdateDto.getUpdated_by());
                        Optional<Boolean> update = answerRepository.update(body);
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
                .code(202)
                .build()));
    }

    @Override
    public ResponseEntity<Data<Answer>> get(Long aLong) {
        try {
            Optional<Answer> answer = answerRepository.get(aLong);
            if (answer.isPresent()) {
                return new ResponseEntity<>(new Data<>(answer.get()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .friendlyMessage("failed")
                .code(202)
                .build()));
    }

    @Override
    public ResponseEntity<Data<List<Answer>>> getAll(AnswerCriteria criteria) {
        try {
            Optional<List<Answer>> all = answerRepository.getAll();
            if (all.isPresent()) {
                return new ResponseEntity<>(new Data<>(all.get()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .code(203)
                .friendlyMessage("failed")
                .build()));
    }

    @Override
    public ResponseEntity<Data<Boolean>> delete(Long aLong) {
        try {
            Optional<Boolean> delete = answerRepository.delete(aLong);
            if (delete.isPresent()) {
                if (delete.get().equals(true)) {
                    return new ResponseEntity<>(new Data<>(true));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .code(203)
                .friendlyMessage("failed")
                .build()));
    }
}
