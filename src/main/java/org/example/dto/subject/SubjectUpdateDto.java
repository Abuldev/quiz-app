package org.example.dto.subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.auth.Question;
import org.example.dto.GenericEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SubjectUpdateDto extends GenericEntity {
    private Long updated_by;
    private LocalDateTime updated_at;
    private String name;

    private String description;

    @Builder
    public SubjectUpdateDto(Long id, Long updated_by,  String name, String description) {
        super(id);
        this.updated_by = updated_by;
        this.updated_at = LocalDateTime.now();
        this.name = name;
        this.description = description;
    }
}
