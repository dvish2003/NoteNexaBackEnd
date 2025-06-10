package lk.ijse.notenexabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private UUID id;
    private String email;
    private String name;
    private String password;
    private String Role;
    private boolean verified;
    private String verificationCode;

}
