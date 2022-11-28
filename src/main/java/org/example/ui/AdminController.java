package org.example.ui;

import org.example.utils.Reader;
import org.example.utils.Writer;

public class AdminController {
    public static void controller() {
        while (true) {
            switch (Reader.readLine("1 -> subject crud\n" +
                    "2 -> user crud\n" +
                    "3 -> History (all quizzes)" +
                    "\n" +
                    "4 -> log out\n" +
                    "q -> quit")) {
                case "1" -> subjectCrud();
                case "2" -> userCrud();
                case "3" -> history();
                case "4" -> {Session.setSessionUser(null);
                    return;}
                case "q" -> {return;}
                default -> controller();
            }
        }
    }

    private static void history() {
        Application.quizGetAll();
    }

    private static void userCrud() {
        while (true) {
            switch (Reader.readLine("1 -> user create\n" +
                    "2 -> user update\n" +
                    "3 -> user get\n" +
                    "4 -> user delete\n" +
                    "5 -> users get\n" +
                    "6 -> log out\n" +
                    "q -> Quit")) {
                case "1" -> Application.userCreate();
                case "2" -> Application.userUpdate();
                case "3" -> Application.userGet();
                case "4" -> Application.userDelete();
                case "5" -> Application.userGetAll();
                case "6" -> Session.setSessionUser(null);
                case "q" -> {
                    return;
                }
                default -> Writer.println("wrong choice");
            }
        }
    }

    private static void subjectCrud() {
        while (true) {
            switch (Reader.readLine("1 -> subject create\n" +
                    "2 -> subject update\n" +
                    "3 -> subject get\n" +
                    "4 -> subject delete\n" +
                    "5 -> subjects get\n" +
                    "6 -> log out\n" +
                    "q -> Quit")) {
                case "1" -> Application.subjectCreate();
                case "2" -> Application.subjectUpdate();
                case "3" -> Application.subjectGet();
                case "4" -> Application.subjectDelete();
                case "5" -> Application.subjectGetAll();
                case "6" -> Session.setSessionUser(null);
                case "q" -> {
                    return;
                }
                default -> Writer.println("wrong choice");
            }
        }
    }
}

