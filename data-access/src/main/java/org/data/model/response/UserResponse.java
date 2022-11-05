package org.data.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
public class UserResponse {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String message;
}
