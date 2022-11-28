package org.example.ui;

import org.example.config.ApplicationContextHolder;
import org.example.domain.auth.AuthUser;
import org.example.domain.role.UserRole;
import org.example.dto.auth.UserCreateDto;
import org.example.dto.auth.UserDto;
import org.example.response.Data;
import org.example.response.ResponseEntity;
import org.example.service.auth.UserService;
import org.example.utils.Reader;
import org.example.utils.Writer;

public class Menu {
    private static final UserService userService = ApplicationContextHolder.getBean(UserService.class);
    public static void controller(){
        while (true) {
            switch (Reader.readLine("1 -> Login\n" +
                    "2 -> Register\n" +
                    "q -> Quit")) {
                case "1": {
                    login();
                    break;
                }
                case "2": {
                    register_();
                    break;
                }
                case "q": {
                    System.exit(1);
                }
                default:
                    Writer.println("wrong choice");
            }
        }
    }

    private static void register_() {
        ResponseEntity<Data<Boolean>> dataResponseEntity = userService.create(UserCreateDto.builder()
                .role(UserRole.USER)
                .username(Reader.readLine("username: "))
                .password(Reader.readLine("password: "))
                .language(Application.chooseLanguage())
                .created_by(-1L)
                .build());

        if (dataResponseEntity.getData().getIsOK().equals(true)) {
            Writer.println(dataResponseEntity.getData().getBody());
        } else Writer.println(dataResponseEntity.getData().getErrorDto().getFriendlyMessage());
    }

    private static void login() {
        ResponseEntity<Data<AuthUser>> login = userService.login(Reader.readLine("username: "), Reader.readLine("password: "));

        if (login.getData().getIsOK().equals(true)) {
            AuthUser body = login.getData().getBody();
            UserDto build = UserDto.builder()
                    .id(body.getId())
                    .username(body.getUsername())
                    .password(body.getPassword())
                    .role(body.getRole())
                    .language(body.getLanguage())
                    .build();

            Session.setSessionUser(build);

            switch (Session.sessionUser.getRole()){
                case ADMIN -> AdminController.controller();
                case TEACHER -> TeacherController.controller();
                default -> UserController.controller();
            }
        }
    }
}
