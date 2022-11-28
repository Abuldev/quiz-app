package org.example.dto.answer;


import lombok.*;
import org.example.dto.BaseEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerCreateDto implements BaseEntity {
    private Long created_by;
    private String answer;
    private Boolean isTrue;
    private Long question_id;
}
