package org.example.ui;

import org.example.config.ApplicationContextHolder;
import org.example.domain.auth.AuthUser;
import org.example.dto.auth.UserUpdateDto;
import org.example.response.Data;
import org.example.response.ResponseEntity;
import org.example.service.auth.UserService;
import org.example.utils.Reader;
import org.example.utils.Writer;

public class TeacherController {
    private static final UserService userService = ApplicationContextHolder.getBean(UserService.class);

    public static void controller() {
        switch (Reader.readLine("1 -> question crud\n" +
                "2 -> options\n" +
                "3 -> History (all quizzes)" +
                "\n" +
                "4 -> log out\n" +
                "q -> quit")) {
            case "1" -> questionCrud();
            case "2" -> optionsCrud();
            case "3" -> history();
            case "4" -> {Session.setSessionUser(null);
            return;}
            case "q" -> {return;}
            default -> Writer.println("wrong choice");
        }
    }

    private static void history() {
        Application.quizGetAll();
    }

    private static void optionsCrud() {
        while (true) {
            switch (Reader.readLine("1 -> update\n" +
                    "2 -> get\n" +
                    "3 -> delete)" +
                    "\n" +
                    "o -> log out\n" +
                    "q -> quit")) {
                case "1" -> update_();
                case "2" -> get();
                case "3" -> delete();
                case "o" -> Session.setSessionUser(null);
                case "q" -> System.exit(0);
                default -> {return;}
            }
        }
    }

    private static void questionCrud() {
        while (true) {
            switch (Reader.readLine("1 -> question create\n" +
                    "2 -> questions get\n" +
                    "3 -> question delete)" +
                    "\n" +
                    "4 -> log out\n" +
                    "q -> quit")) {
                case "1" -> Application.questionCreate();
                case "2" -> Application.questionGetAll();
                case "3" -> Application.questionDelete();
                case "4" -> Session.setSessionUser(null);
                case "q" -> {return;}
                default -> Writer.println("wrong choice");
            }
        }
    }


    public static void update_() {
        try {
            userService.update(UserUpdateDto.childBuilder()
                    .username(Reader.readLine("username: "))
                    .password(Reader.readLine("password: "))
                    .language(Application.chooseLanguage())
                    .id(Session.sessionUser.getId())
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void get() {
        try {
            ResponseEntity<Data<AuthUser>> dataResponseEntity = userService.get(Session.sessionUser.getId());

            if (dataResponseEntity.getData().getIsOK().equals(true)) {
                Writer.println(dataResponseEntity.getData().getBody());
            } else Writer.println(dataResponseEntity.getData().getErrorDto().getFriendlyMessage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void delete() {
       try {
           ResponseEntity<Data<Boolean>> delete = userService.delete(Session.sessionUser.getId());

           if (delete.getData().getIsOK().equals(true)) {
               Writer.println(delete.getData().getBody());
           } else Writer.println(delete.getData().getErrorDto().getFriendlyMessage());
       } catch (Exception e){
           e.printStackTrace();
       }
    }
}