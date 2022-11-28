package org.example.service.auth;

import org.example.criteria.auth.QuizQuestionCriteria;
import org.example.domain.auth.QuizQuestion;
import org.example.dto.qq.QuizQuestionCreateDto;
import org.example.dto.qq.QuizQuestionUpdate;
import org.example.response.Data;
import org.example.response.ResponseEntity;
import org.example.service.Service;
import org.example.service.ServiceCRUD;

import java.util.List;

public class QuizQuestionService implements Service, ServiceCRUD<
        QuizQuestionCreateDto, QuizQuestionUpdate, Long, QuizQuestionCriteria, QuizQuestion> {
    @Override
    public ResponseEntity<Data<Boolean>> create(QuizQuestionCreateDto quizQuestionCreateDto) {
        return null;
    }

    @Override
    public ResponseEntity<Data<Boolean>> update(QuizQuestionUpdate quizQuestionUpdate) {
        return null;
    }

    @Override
    public ResponseEntity<Data<QuizQuestion>> get(Long aLong) {
        return null;
    }

    @Override
    public ResponseEntity<Data<List<QuizQuestion>>> getAll(QuizQuestionCriteria criteria) {
        return null;
    }

    @Override
    public ResponseEntity<Data<Boolean>> delete(Long aLong) {
        return null;
    }
}
