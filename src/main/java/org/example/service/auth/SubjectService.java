package org.example.service.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.config.ApplicationContextHolder;
import org.example.criteria.auth.SubjectCriteria;
import org.example.domain.auth.Subject;
import org.example.dto.subject.SubjectCreateDto;
import org.example.dto.subject.SubjectUpdateDto;
import org.example.repository.auth.SubjectRepository;
import org.example.response.Data;
import org.example.response.ResponseEntity;
import org.example.service.Service;
import org.example.service.ServiceCRUD;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubjectService implements Service, ServiceCRUD<
        SubjectCreateDto, SubjectUpdateDto, Long, SubjectCriteria, Subject> {
    private static SubjectService instance;
    private static SubjectRepository subjectRepository = ApplicationContextHolder.getBean(SubjectRepository.class);

    public static SubjectService getInstance() {
        if (instance == null) {
            instance = new SubjectService();
        }
        return instance;
    }
    @Override
    public ResponseEntity<Data<Boolean>> create(SubjectCreateDto subjectCreateDto) {

        try {
            Optional<Boolean> save = subjectRepository.save(Subject.builder()
                    .createdBy(subjectCreateDto.getCreated_by())
                    .code(subjectCreateDto.getCode())
                    .name(subjectCreateDto.getName())
                    .description(subjectCreateDto.getDescription())
                    .build());

            if (save.isPresent()) {
                if (save.get().equals(true)) {
                    return new ResponseEntity<>(new Data<>(true));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .code(404)
                .developerMessage("failed")
                .friendlyMessage("failed")
                .build()));
    }

    @Override
    public ResponseEntity<Data<Boolean>> update(SubjectUpdateDto subjectUpdateDto) {
        try {

            ResponseEntity<Data<Subject>> dataResponseEntity = get(subjectUpdateDto.id);

            if (dataResponseEntity.getData().getIsOK().equals(true)) {
                Subject body = dataResponseEntity.getData().getBody();
                body.setUpdatedBy(subjectUpdateDto.getUpdated_by());
                body.setName(subjectUpdateDto.getName());
                body.setDescription(subjectUpdateDto.getDescription());
                Optional<Boolean> update = subjectRepository.update(body);

                if (update.isPresent()) {
                    if (update.get().equals(true)) {
                        return new ResponseEntity<>(new Data<>(true));
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .code(404)
                .developerMessage("failed")
                .friendlyMessage("failed")
                .build()));
    }

    @Override
    public ResponseEntity<Data<Subject>> get(Long aLong) {
        try {
            Optional<Subject> subject = subjectRepository.get(aLong);

            if (subject.isPresent()) {
                return new ResponseEntity<>(new Data<>(subject.get()));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .code(404)
                .developerMessage("failed")
                .friendlyMessage("failed")
                .build()));

    }

    @Override
    public ResponseEntity<Data<List<Subject>>> getAll(SubjectCriteria criteria) {
        try {
            Optional<List<Subject>> all = subjectRepository.getAll();

            if (all.isPresent()) {
                return new ResponseEntity<>( new Data<>(all.get()));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .code(404)
                .developerMessage("failed")
                .friendlyMessage("failed")
                .build()));

    }

    @Override
    public ResponseEntity<Data<Boolean>> delete(Long aLong) {
        try {
            Optional<Boolean> delete = subjectRepository.delete(aLong);

            if (delete.isPresent()) {
                if (delete.get().equals(true)) {
                    return new ResponseEntity<>(new Data<>(true));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .code(404)
                .developerMessage("failed")
                .friendlyMessage("failed")
                .build()));

    }
}
