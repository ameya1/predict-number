package org.data.model.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
