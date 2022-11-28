package org.example.domain.auth;


import jakarta.persistence.*;
import lombok.*;
import org.example.domain.Domain;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;
import org.hibernate.type.NumericBooleanConverter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Where(clause = "deleted = 0")
public class Answer implements Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false, name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;


    private Long question_id;
    @Builder.Default
    @Convert(converter = NumericBooleanConverter.class)
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "question_id_")
    private Question question;


    @Column(nullable = false)
    private String answer;

    @Builder.Default
    @Convert(converter = NumericBooleanConverter.class)
    private Boolean isTrue = false;


}
