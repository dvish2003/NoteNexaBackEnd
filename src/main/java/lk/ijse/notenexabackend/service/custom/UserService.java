package lk.ijse.notenexabackend.service.custom;



import lk.ijse.notenexabackend.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUser();


    List<UserDTO> getUsers();


    int saveUser(UserDTO userDTO);

    int verifyUser(String email, String code);

    UserDTO searchUser(String username);
}