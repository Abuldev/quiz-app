package org.example.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseEntity <B>  {
    private B data;
    private Integer status;

    public ResponseEntity(B data) {
        this.data = data;
        this.status = 200;
    }
}
