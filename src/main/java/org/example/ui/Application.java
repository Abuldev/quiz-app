package org.example.ui;

import org.example.config.ApplicationContextHolder;
import org.example.criteria.auth.*;
import org.example.domain.auth.*;
import org.example.domain.role.LanguageEnum;
import org.example.domain.role.QuizLevel;
import org.example.domain.role.UserRole;
import org.example.dto.answer.AnswerCreateDto;
import org.example.dto.answer.AnswerUpdateDto;
import org.example.dto.auth.UserCreateDto;
import org.example.dto.auth.UserUpdateDto;
import org.example.dto.question.QuestionCreateDto;
import org.example.dto.question.QuestionUpdateDto;
import org.example.dto.quiz.QuizCreateDto;
import org.example.dto.subject.SubjectCreateDto;
import org.example.dto.subject.SubjectUpdateDto;
import org.example.response.Data;
import org.example.response.ResponseEntity;
import org.example.service.auth.*;
import org.example.utils.Reader;
import org.example.utils.Writer;
import org.hibernate.sql.exec.ExecutionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.utils.Color.BLUE;

public class Application {
    private static final QuizService quizService = ApplicationContextHolder.getBean(QuizService.class);
    private static final UserService userService = ApplicationContextHolder.getBean(UserService.class);
    private static final AnswerService answerService = ApplicationContextHolder.getBean(AnswerService.class);
    private static final QuestionService questionService = ApplicationContextHolder.getBean(QuestionService.class);
    private static final SubjectService subjectService = ApplicationContextHolder.getBean(SubjectService.class);

    public static void main(String[] args) {
        Menu.controller();
        /////// user ///////
//        userCreate();
//        userUpdate();
//        userGet();
//        userDelete();
//        userGetAll();

        ///////// quiz //////////
//        quizCreate();
//        quizGet();
//        quizDelete();
//        quizGetAll();

        /////////// answer //////////

//        answerCreate();
//        answerUpdate();
//        answerGet();
//        answerGetAll();
//        answerDelete();


        ////////// question /////////
        questionCreate();
//        questionUpdate();
//        questionGet();
//        questionDelete();
//        questionGetAll();


        ///////////// subject ///////////
//        subjectCreate();
    }

    public static void subjectCreate() {

        ResponseEntity<Data<Boolean>> dataResponseEntity1 = subjectService.create(SubjectCreateDto.builder()
                .created_by(Session.sessionUser.getId())
                .code(Reader.readLine("code: "))
                .name(Reader.readLine("Subject: "))
                .description(Reader.readLine("description: "))
                .build());

        if (dataResponseEntity1.getData().getIsOK().equals(true)) {
            Writer.println(dataResponseEntity1.getData().getBody());
        } else Writer.println(dataResponseEntity1.getData().getErrorDto().getFriendlyMessage());
    }

    public static void questionGetAll() {
        ResponseEntity<Data<List<Question>>> all = questionService.getAll(new QuestionCriteria());
        if (all.getData().getIsOK().equals(true)) {
            Writer<Question> questionWriter = new Writer<>();
            questionWriter.print(all);
        } else Writer.println(all.getData().getErrorDto().getFriendlyMessage());
    }

    public static void questionDelete() {
        ResponseEntity<Data<Boolean>> delete = questionService.delete(1L);
        if (delete.getData().getIsOK().equals(true)) {
            Writer.println(delete.getData().getBody());
        } else Writer.println(delete.getData().getErrorDto().getFriendlyMessage());
    }

    public static void questionGet() {
        ResponseEntity<Data<Question>> dataResponseEntity = questionService.get(11L);
        if (dataResponseEntity.getData().getIsOK().equals(true)) {
            Writer.println(dataResponseEntity.getData().getBody());
        } else Writer.println(dataResponseEntity.getData().getErrorDto().getFriendlyMessage());
    }

    public static void questionUpdate() {
        ResponseEntity<Data<Boolean>> ssss = questionService.update(QuestionUpdateDto.builder()
                .id(1L)
                .question("ssss")
                .subject_id(-1L)
                .level(QuizLevel.HARD)
                .language(LanguageEnum.EN)
                .build());
        if (ssss.getData().getIsOK().equals(true)) {
            Writer.println(ssss.getData().getBody());
        } else Writer.println(ssss.getData().getErrorDto().getFriendlyMessage());
    }

    public static void questionCreate() {
        QuestionCreateDto build = QuestionCreateDto
                .builder()
                .created_by(Session.sessionUser.getId())
                .question(Reader.readLine("question: "))
                .language(chooseLanguage())
                .level(chooseLevel())
                .answers(createAnswersToQuestion())
                .build();
        Long aLong = chooseSubjectId();
        if (Objects.isNull(aLong)) {
            Writer.println("Subject not found");
            return;
        }
        build.setSubject_id(aLong);
        ResponseEntity<Data<Boolean>> dataResponseEntity = questionService.create(build);
        if (dataResponseEntity.getData().getIsOK().equals(true)) {
            Writer.println(dataResponseEntity.getData().getBody());
        } else Writer.println(dataResponseEntity.getData().getErrorDto().getFriendlyMessage());
    }

    public static List<Answer> createAnswersToQuestion() {
        Writer.println("Answers: ");
        Answer answer1 = Answer.builder()
                .answer(Reader.readLine("1. Answer : Enter correct answer: "))
                .createdBy(Session.sessionUser.getId())
                .isTrue(Boolean.TRUE)
                .build();

        Answer.AnswerBuilder builder = Answer.builder();
        builder.answer(Reader.readLine("2. Answer : Enter incorrect answer: "));
        builder.createdBy(Session.sessionUser.getId());
        builder.isTrue(Boolean.FALSE);
        Answer answer2 = builder
                .build();


        Answer answer3 = Answer.builder()
                .answer(Reader.readLine("3. Answer : Enter incorrect answer: "))
                .createdBy(Session.sessionUser.getId())
                .isTrue(Boolean.TRUE)
                .build();

        return List.of(answer1, answer2, answer3);
    }

    public static Long chooseSubjectId() {
        ResponseEntity<Data<List<Subject>>> all = subjectService.getAll(new SubjectCriteria());
        if (all.getData().getIsOK().equals(true)) {
            List<Subject> body = all.getData().getBody();

            for (int i = 0; i < body.size(); i++) {
                Writer.println(i + 1 + ") " + body.get(i).getName());
            }

            String enter_subject_name = Reader.readLine("Enter subject name");
            Long subject_id = 0L;
            for (int i = 0; i < body.size(); i++) {
                if (body.get(i).getName().contains(enter_subject_name)) {
                    Writer.println(body.get(i).getName());
                    String s = Reader.readLine("y/n:  ?");
                    if (s.equalsIgnoreCase("y")) {
                        subject_id = body.get(i).getId();
                        break;
                    } else if (s.equalsIgnoreCase("n")) {
                        continue;
                    } else {
                        subject_id = body.get(i).getId();
                        break;
                    }
                }

                if (i == body.size() - 1 && subject_id == 0L) {
                    Writer.println("subject not found");
                }
            }

            if (subject_id != 0L) {
                return subject_id;
            }

        }
        return null;
    }

    public static QuizLevel chooseLevel() {
        return switch (Reader.readLine("Choose Level: \n" +
                "1. EASY\n" +
                "2. MEDIUM\n" +
                "3. HARD\n" +
                "default \"EASY\"")) {
            case "3" -> QuizLevel.HARD;
            case "2" -> QuizLevel.MEDIUM;
            default -> QuizLevel.EASY;
        };
    }

    public static LanguageEnum chooseLanguage() {
        return switch (Reader.readLine("Choose language: \n" +
                "1. EN\n" +
                "2. RU\n" +
                "3. UZ\n" +
                "default \"UZ\"")) {
            case "1" -> LanguageEnum.EN;
            case "2" -> LanguageEnum.RU;
            default -> LanguageEnum.UZ;
        };
    }

    public static void quizGetAll() {
        ResponseEntity<Data<List<Quiz>>> all = quizService.getAll(new QuizCriteria());
        if (all.getData().getIsOK().equals(true)) {
            Writer.println(all.getData().getBody());
        } else Writer.println(all.getData().getErrorDto().getFriendlyMessage());
    }

    public static void quizDelete() {
        ResponseEntity<Data<Boolean>> delete = quizService.delete(1L);
        if (delete.getData().getIsOK().equals(true)) {
            Writer.println(delete.getData().getBody());
        } else Writer.println(delete.getData().getErrorDto().getFriendlyMessage());
    }

    public static void quizGet() {
        ResponseEntity<Data<Quiz>> dataResponseEntity = quizService.get(1L);
        if (dataResponseEntity.getData().getIsOK().equals(true)) {
            Writer.println(dataResponseEntity.getData().getBody());
        } else Writer.println(dataResponseEntity.getData().getErrorDto().getFriendlyMessage());
    }

    public static void userGetAll() {
        ResponseEntity<Data<List<AuthUser>>> all = userService.getAll(new UserCriteria());
        if (all.getData().getIsOK().equals(true)) {
            for (int i = 0; i < all.getData().getBody().size(); i++) {
                Writer.println(i + 1 + ") " + all.getData().getBody().get(i));
            }
        } else Writer.println(all.getData().getErrorDto().getFriendlyMessage());
    }

    public static void userDelete() {
        ResponseEntity<Data<Boolean>> delete = userService.delete(Reader.readLong("id: "));
        if (delete.getData().getIsOK().equals(true)) {
            Writer.println(delete.getData().getBody());
        } else Writer.println(delete.getData().getErrorDto().getFriendlyMessage());
    }

    public static void answerDelete() {
        ResponseEntity<Data<Boolean>> delete = answerService.delete(1L);
        if (delete.getData().getIsOK().equals(true)) {
            Writer.println(delete.getData().getBody());
        } else Writer.println(delete.getData().getErrorDto().getFriendlyMessage());
    }

    public static void answerGetAll() {
        ResponseEntity<Data<List<Answer>>> all = answerService.getAll(new AnswerCriteria());
        if (all.getData().getIsOK().equals(true)) {
            Writer.println(all.getData().getBody());
        } else Writer.println(all.getData().getErrorDto().getFriendlyMessage());
    }

    public static void answerGet() {
        ResponseEntity<Data<Answer>> dataResponseEntity = answerService.get(1L);
        if (dataResponseEntity.getData().getIsOK().equals(true)) {
            Writer.println(dataResponseEntity.getData().getBody());
        } else Writer.println(dataResponseEntity.getData().getErrorDto().getFriendlyMessage());
    }

    public static void answerUpdate() {
        ResponseEntity<Data<Boolean>> salom = answerService.update(AnswerUpdateDto.builder()
                .answer("saloom")
                .isTrue(false)
                .updated_by(1L)
                .id(1L)
                .build());
        if (salom.getData().getIsOK().equals(true)) {
            Writer.println(salom.getData().getBody());
        } else Writer.println(salom.getData().getErrorDto().getFriendlyMessage());
    }

    public static void answerCreate() {
        ResponseEntity<Data<Boolean>> ans = answerService.create(AnswerCreateDto.builder()
                .created_by(1L)
                .isTrue(true)
                .question_id(1L)
                .answer("meni yoshiiim nechida")
                .build());
        if (ans.getData().getIsOK().equals(true)) {
            Writer.println(ans.getData().getBody());
        } else Writer.println(ans.getData().getErrorDto().getFriendlyMessage());
    }

    public static void userGet() {
        ResponseEntity<Data<AuthUser>> dataResponseEntity = userService.get(Reader.readLong("id: "));
        if (dataResponseEntity.getData().getIsOK().equals(true)) {
            Writer.println(dataResponseEntity.getData().getBody());
        } else Writer.println(dataResponseEntity.getData().getErrorDto().getFriendlyMessage());
    }

    public static void userUpdate() {
        ResponseEntity<Data<Boolean>> name = userService.update(UserUpdateDto.childBuilder()
                .id(Reader.readLong("id: "))
                .username(Reader.readLine("username: "))
                .language(chooseLanguage())
                .password(Reader.readLine("password: "))
                .build());
        if (name.getData().getIsOK().equals(true)) {
            Writer.println(name.getData().getBody());
        } else Writer.println(name.getData().getErrorDto().getFriendlyMessage());
    }

    public static void quizCreate() {
        ResponseEntity<Data<Boolean>> quiz1 = quizService.create(QuizCreateDto.builder()
                .quizCount(3)
                .quizLevel(QuizLevel.HARD)
                .quizName("quiz1")
                .subject_id(-1L)
                .languageEnum(LanguageEnum.UZ)
                .quizQuestions(null)
                .build());
        if (quiz1.getData().getIsOK()) {
            Writer.println(quiz1.getData().getBody());
        } else Writer.println(quiz1.getData().getErrorDto().getFriendlyMessage());
    }

    public static void userCreate() {
        ResponseEntity<Data<Boolean>> john = userService.create(UserCreateDto.builder()
                .username(Reader.readLine("username: "))
                .password(Reader.readLine("password: "))
                .language(chooseLanguage())
                .role(chooseRole())
                .build());

        if (john.getData().getIsOK().equals(true)) {
            Writer.println(true);
        } else Writer.println(john.getData().getErrorDto().getFriendlyMessage());
    }

    private static UserRole chooseRole() {
        return switch (Reader.readLine("choose role: \n" +
                "1. ADMIN\n" +
                "2. Teacher\n" +
                "3. USER\n" +
                "default \"USER\"")) {
            case "1" -> UserRole.ADMIN;
            case "2" -> UserRole.TEACHER;
            default -> UserRole.USER;
        };
    }

    public static void subjectUpdate() {
        ResponseEntity<Data<Boolean>> update = subjectService.update(SubjectUpdateDto.builder()
                .id(Reader.readLong("id: "))
                .updated_by(Session.sessionUser.getId())
                .name(Reader.readLine("subject: "))
                .description(Reader.readLine("description: "))
                .build());

        if (update.getData().getIsOK().equals(true)) {
            Writer.println(update.getData().getBody());
        } else Writer.println(update.getData().getErrorDto().getFriendlyMessage());
    }

    public static void subjectGet() {
        ResponseEntity<Data<Subject>> dataResponseEntity = subjectService.get(Reader.readLong("id: "));

        if (dataResponseEntity.getData().getIsOK().equals(true)) {
            Writer.println(dataResponseEntity.getData().getBody());
        } else Writer.println(dataResponseEntity.getData().getErrorDto().getFriendlyMessage());
    }

    public static void subjectDelete() {
        ResponseEntity<Data<Boolean>> delete = subjectService.delete(Reader.readLong("id: "));

        if (delete.getData().getIsOK().equals(true)) {
            Writer.println(delete.getData().getBody());
        } else Writer.println(delete.getData().getErrorDto().getFriendlyMessage());
    }

    public static void subjectGetAll() {
        ResponseEntity<Data<List<Subject>>> all = subjectService.getAll(new SubjectCriteria());
        if (all.getData().getIsOK().equals(true)) {
            Writer.println(all.getData().getBody());
        } else Writer.println(all.getData().getErrorDto().getFriendlyMessage());
    }
}
