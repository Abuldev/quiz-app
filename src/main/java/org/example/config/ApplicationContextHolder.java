package org.example.config;

import org.example.repository.auth.*;
import org.example.service.auth.*;

public class ApplicationContextHolder {

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return switch (beanName) {
            case "UserService" -> (T) UserService.getInstance();
            case "UserRepository" -> (T) UserRepository.getInstance();
            case "QuizService" -> (T) QuizService.getInstance();
            case "QuizRepository" -> (T) QuizRepository.getInstance();
            case "AnswerService" -> (T) AnswerService.getInstance();
            case "AnswerRepository" -> (T) AnswerRepository.getInstance();
            case "QuestionService" -> (T) QuestionService.getInstance();
            case "QuestionRepository" -> (T) QuestionRepository.getInstance();
            case "SubjectService" -> (T) SubjectService.getInstance();
            case "SubjectRepository" -> (T) SubjectRepository.getInstance();
            case "QuizQuestionRepository" -> (T) QuizQuestionRepository.getInstance();

            default -> throw new RuntimeException("Bean Not Found");
        };
    }

    public static <T> T getBean(Class<T> clazz) {
        String beanName = clazz.getSimpleName();
        return getBean(beanName);
    }

}
