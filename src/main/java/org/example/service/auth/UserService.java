package org.example.service.auth;

import org.example.config.ApplicationContextHolder;
import org.example.criteria.auth.UserCriteria;
import org.example.domain.auth.AuthUser;
import org.example.dto.auth.UserCreateDto;
import org.example.dto.auth.UserUpdateDto;
import org.example.repository.auth.UserRepository;
import org.example.response.Data;
import org.example.response.ResponseEntity;
import org.example.service.Service;
import org.example.service.ServiceCRUD;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserService implements Service, ServiceCRUD<
        UserCreateDto, UserUpdateDto, Long, UserCriteria, AuthUser> {

    private UserRepository userRepository = ApplicationContextHolder.getBean(UserRepository.class);

    private UserService(){}
    private static UserService instance;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public ResponseEntity<Data<AuthUser>> login(String username, String password){
      try {
          Optional<AuthUser> login = userRepository.login(username, password);

          if (login.isPresent()) {
              return new ResponseEntity<>(new Data<>(login.get()));
          }
      }catch (Exception e){
          e.printStackTrace();
      }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .friendlyMessage("failed")
                .build()), 404);
    }


    @Override
    public ResponseEntity<Data<Boolean>> create(UserCreateDto userCreateDto) {
        try {
            Optional<Boolean> save = userRepository.save(AuthUser.builder()
                    .username(userCreateDto.getUsername())
                    .password(userCreateDto.getPassword())
                    .createdBy(-1l)
                    .role(userCreateDto.getRole())
                    .language(userCreateDto.getLanguage())
                    .build());

            if (save.isPresent()) {
                if (save.get().equals(true)) {
                    new ResponseEntity<>(new Data<>(true));
                }
            }
        } catch (Exception e){
            return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                    .friendlyMessage(e.getMessage())
                    .build()), 400);
        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .friendlyMessage("failed")
                .build()), 400);
    }

    @Override
    public ResponseEntity<Data<Boolean>> update(UserUpdateDto userUpdateDto) {
        ResponseEntity<Data<AuthUser>> dataResponseEntity = get(userUpdateDto.id);
        if (dataResponseEntity.getData().getIsOK().equals(true)){
            AuthUser body = dataResponseEntity.getData().getBody();

            Optional<Boolean> update = userRepository.update(AuthUser.builder()
                            .language (userUpdateDto.getLanguage())
                            .password (userUpdateDto.getPassword())
                            .username (userUpdateDto.getUsername())
                            .updatedAt (LocalDateTime.now())
                            .updatedBy (-1L)
                            .createdAt (body.getCreatedAt())
                            .createdBy(body.getCreatedBy())
                            .deleted (body.isDeleted())
                            .isActive (body.getIsActive())
                            .role (body.getRole())
                            .tryCount (body.getTryCount())
                            .id (userUpdateDto.id)
                    .build());
            if (update.isPresent()) {
                if (update.get().equals(true)) {
                    return new ResponseEntity<>(new Data<>(true));
                }
            }

        }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .code(404)
                .friendlyMessage("failed")
                .build()));
    }

    @Override
    public ResponseEntity<Data<AuthUser>> get(Long aLong) {
        Optional<AuthUser> authUser = userRepository.get(aLong);
        return authUser.map(user -> new ResponseEntity<>(new Data<>(user))).orElseGet(() -> new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .code(211)
                .friendlyMessage("failed")
                .build())));
    }

    @Override
    public ResponseEntity<Data<List<AuthUser>>> getAll(UserCriteria criteria) {
      try {
          Optional<List<AuthUser>> all = userRepository.getAll();
          if (all.isPresent()) {
              return new ResponseEntity<>(new Data<>(all.get()));
          }
      } catch (Exception e){
          e.printStackTrace();
      }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .code(211)
                .friendlyMessage("failed")
                .build()));
    }

    @Override
    public ResponseEntity<Data<Boolean>> delete(Long aLong) {
      try {
          Optional<Boolean> delete = userRepository.delete(aLong);
          if (delete.isPresent()) {
              if (delete.get().equals(true)) {
                  return new ResponseEntity<>(new Data<>(true));
              }
          }
      }catch (Exception e){
          e.printStackTrace();
      }
        return new ResponseEntity<>(new Data<>(Data.errorBuilder()
                .code(211)
                .friendlyMessage("failed")
                .build()));
    }
}
