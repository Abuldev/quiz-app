package org.example.dto.subject;

import lombok.*;
import org.example.domain.auth.Question;
import org.example.dto.BaseEntity;
import org.example.dto.GenericEntity;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectCreateDto implements BaseEntity {
    private Long created_by;
    private String code;
    private String name;
    private String description;

}
