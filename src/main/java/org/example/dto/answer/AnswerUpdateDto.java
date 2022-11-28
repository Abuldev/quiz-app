package org.example.dto.answer;

import lombok.Builder;
import lombok.Getter;
import org.example.dto.GenericEntity;

import java.time.LocalDateTime;

@Getter
public class AnswerUpdateDto extends GenericEntity {
    private Long updated_by;
    private String answer;
    private Boolean isTrue;
    private LocalDateTime updated_at;
    private Long question_id;

    @Builder
    public AnswerUpdateDto(Long id, Long updated_by, String answer, Boolean isTrue) {
        super(id);
        this.updated_by = updated_by;
        this.answer = answer;
        this.isTrue = isTrue;
        this.updated_at = LocalDateTime.now();
    }
}
