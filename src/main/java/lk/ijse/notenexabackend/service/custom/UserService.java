package lk.ijse.notenexabackend.custom;


import com.vish.saratoga_backend.dto.UserDTO;

import java.util.List;

public interface UserService {
    //get last 4 users

    List<UserDTO> getAllUser();


    List<UserDTO> getUsers();


    int saveUser(UserDTO userDTO);
}