package org.example.dto.answer;

import lombok.*;
import org.example.dto.BaseEntity;

/**
 * @Author :  Asliddin Ziyodullaev
 * @Date :  10:09   22/07/22
 * @Project :  QuizAppTeam
 */


@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class AnswerDto implements BaseEntity {
    private String answer;
    private Boolean isTrue = false;
    private Long question_id;
}
