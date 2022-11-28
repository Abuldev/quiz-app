package org.example.response;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Data <T> {
    private T body;
    private ErrorDto errorDto;
    private T total;
    private Boolean isOK;

    public Data(T body) {
        this.body = body;
        this.isOK = true;
    }

    public Data(T body, T total, Boolean isOK) {
        this.body = body;
        this.total = total;
        this.isOK = true;
    }

    public Data(ErrorDto errorDto) {
        this.errorDto = errorDto;
        this.isOK = false;
    }

    public static ErrorDto errorBuilder() {
        return new ErrorDto();
    }


    @Getter
    @NoArgsConstructor
    public static class ErrorDto {
        private String friendlyMessage;
        private String developerMessage;
        private Integer code = 500;
        private Timestamp created_at;


        public ErrorDto friendlyMessage(String friendlyMessage) {
            this.friendlyMessage = friendlyMessage;
            return this;
        }

        public ErrorDto developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ErrorDto code(Integer status) {
            this.code = status;
            return this;
        }


        private ErrorDto(String friendlyMessage, String developerMessage) {
            this.friendlyMessage = friendlyMessage;
            this.developerMessage = developerMessage;
            this.created_at = Timestamp.valueOf(LocalDateTime.now());
        }

        private ErrorDto(String friendlyMessage, String developerMessage, Integer code) {
            this(friendlyMessage, developerMessage);
            this.code = code;
        }

        public ErrorDto build() {
            return new ErrorDto(this.friendlyMessage, this.developerMessage, this.code);
        }

    }
}
