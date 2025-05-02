package telekom.com.authenticationservice.Dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
