package org.example.dto.subject;

import lombok.Builder;
import lombok.Getter;
import org.example.domain.auth.Question;
import org.example.dto.GenericEntity;

import java.util.List;

@Getter
public class SubjectDto extends GenericEntity {
    private Long created_by;
    private String code;
    private String name;
    private String description;
    private List<Question> questions;

    @Builder
    public SubjectDto(Long id, Long created_by, String code, String name, String description, List<Question> questions) {
        super(id);
        this.created_by = created_by;
        this.code = code;
        this.name = name;
        this.description = description;
        this.questions = questions;
    }
}
