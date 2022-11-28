package org.example.ui;

import org.example.config.ApplicationContextHolder;
import org.example.criteria.auth.QuestionCriteria;
import org.example.domain.auth.Answer;
import org.example.domain.auth.Question;
import org.example.domain.auth.Quiz;
import org.example.domain.auth.QuizQuestion;
import org.example.dto.quiz.QuizCreateDto;
import org.example.repository.auth.AnswerRepository;
import org.example.repository.auth.QuestionRepository;
import org.example.repository.auth.QuizQuestionRepository;
import org.example.repository.auth.QuizRepository;
import org.example.response.Data;
import org.example.response.ResponseEntity;
import org.example.service.auth.QuestionService;
import org.example.utils.Reader;
import org.example.utils.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserController {
    private static final QuizQuestionRepository questionRepository = ApplicationContextHolder.getBean(QuizQuestionRepository.class);
    private static final QuizRepository quizRepository = ApplicationContextHolder.getBean(QuizRepository.class);
    private static final AnswerRepository answerRepository = ApplicationContextHolder.getBean(AnswerRepository.class);
    private static final QuestionService questionService = ApplicationContextHolder.getBean(QuestionService.class);
    private static final QuestionRepository questionRepositories = ApplicationContextHolder.getBean(QuestionRepository.class);

    public static void controller() {
        while (true) {
            switch (Reader.readLine("\n\n1 -> solve quiz\n" +
                    "=================================\n" +
                    "2 -> update\n" +
                    "3 -> get\n" +
                    "4 -> delete\n" +
                    "5 -> log out\n" +
                    "q -> Quit")) {
                case "1" -> solveQuiz();
                case "2" -> TeacherController.update_();
                case "3" -> TeacherController.get();
                case "4" -> TeacherController.delete();
                case "5" -> {Session.setSessionUser(null);
                    return;}
                case "q" -> {
                   return;
                }
            }
        }
    }

    private static void solveQuiz() {

        Quiz last1 = quizRepository.getLast();

        long increase = last1.getId() + 1L;

        Writer.println("Quiz name : quiz" + increase);
        Writer.println("student: " + Session.sessionUser.getUsername());
        System.out.println();
        System.out.println();
        System.out.println();

        QuizCreateDto build = QuizCreateDto.builder()
                .languageEnum(Application.chooseLanguage())
                .created_by(Session.sessionUser.getId())
                .quizLevel(Application.chooseLevel())
                .quizName("quiz" + increase)
                .build();

        Long aLong = Application.chooseSubjectId();
        if (Objects.isNull(aLong)){
            return;
        }
        build.setSubject_id(aLong);
        int integer = Reader.readInt("question count: ");


        build.setQuizCount(integer);

        Optional<Boolean> save = quizRepository.save(Quiz.builder()
                .createdBy(Session.sessionUser.getId())
                .language(build.getLanguageEnum())
                .level(build.getQuizLevel())
                .subject_id(build.getSubject_id())
                .quizName(build.getQuizName())
                .build());

        if (save.isEmpty()) {
            Writer.println("quiz was not saved successfully");
            return;
        }


        Quiz last = quizRepository.getLast();

        ResponseEntity<Data<List<Question>>> all = questionService.getAll(new QuestionCriteria());

        if (all.getData().getIsOK().equals(true)) {

            List<Question> body = all.getData().getBody();
            List<Question> checkQuestion = new ArrayList<>();
            for (Question question : body) {
                if (question.getSubject_id().equals(build.getSubject_id())
                        && question.getLanguage().equals(build.getLanguageEnum())
                        && question.getLevel().equals(build.getQuizLevel())) {
                    checkQuestion.add(question);
                }
            }


            List<Question> questionList = new ArrayList<>();


            if (integer > checkQuestion.size()) {
                Writer.println("there are not many questions in the data warehouse");
                return;
            }

            String check = "";
            while (questionList.size() != integer) {
                int rand = (int) (Math.random() * checkQuestion.size());
                if (!check.contains(String.valueOf(rand))) {
                    questionList.add(checkQuestion.get(rand));
                }
            }


            List<Question> questions = new ArrayList<>();
            List<QuizQuestion> quizQuestionList = new ArrayList<>();
            for (int i = 0; i < questionList.size(); i++) {
                QuizQuestion build1 = QuizQuestion.builder()
                        .createdBy(Session.sessionUser.getId())
                        .quiz(last)
                        .question_id(questionList.get(i).getId())
                        .build();
                Optional<Question> question = questionRepositories.get(questionList.get(i).getId());
                Question question1 = question.get();
                questionRepository.save(build1);
                quizQuestionList.add(build1);
                questions.add(question1);
            }


            int i = 0;
            int trueAnswers = 0;
            int size = questionList.size();
            while (size != 0 && !Reader.readLine("do you want continue? \n y/n:").equals("n")) {
                QuizQuestion quizQuestion = quizQuestionList.get(i);
                int j = 1;
                for (Question question : questions) {
                    if (question.getId().equals(quizQuestion.getQuestion_id())) ;
                    String question1 = question.getQuestion();
                    Optional<List<Answer>> all1 = answerRepository.getAll();
                    if (all1.isEmpty()) {
                        Writer.println("answers are empty");
                        return;
                    }
                    List<Answer> answers = all1.get();
                    List<Answer> currentAnswers = new ArrayList<>();
                    for (Answer answer : answers) {
                        if (Objects.equals(answer.getQuestion_id(), question.getId())) {
                            currentAnswers.add(answer);
                        }
                    }

                    String[] arr = new String[]{"A", "B", "C"};
                    int counter = 0;
                    Writer.println(j + "  " + question1 + " ?");
                    for (Answer currentAnswer : currentAnswers) {
                        Writer.println(arr[counter++] + ") " + currentAnswer.getAnswer());
                    }


                    switch (Reader.readLine("your answer: ").toLowerCase()) {
                        case "a" -> quizQuestion.setIsRight(currentAnswers.get(0).getIsTrue());
                        case "b" -> quizQuestion.setIsRight(currentAnswers.get(1).getIsTrue());
                        case "c" -> quizQuestion.setIsRight(currentAnswers.get(2).getIsTrue());
                        default -> quizQuestion.setIsRight(false);
                    }

                    if (quizQuestion.getIsRight().equals(true)) {
                        trueAnswers++;
                        last.setBall((trueAnswers * 100) / integer);
                    }

                    size--;
                }

            }

            Writer.println(trueAnswers + "/" + integer + " in percent: " + last.getBall()+" %");

            Optional<Boolean> update = quizRepository.update(Quiz.builder()
                    .id(last.getId())
                    .createdBy(Session.sessionUser.getId())
                    .createdAt(last.getCreatedAt())
                    .language(build.getLanguageEnum())
                    .updatedBy(Session.sessionUser.getId())
                    .level(build.getQuizLevel())
                    .subject_id(build.getSubject_id())
                    .quizName(build.getQuizName())
                    .ball(last.getBall())
                    .build());

            if (update.isEmpty()) {
                Writer.println("quiz update was failed");
                return;
            }
        }
    }
}
