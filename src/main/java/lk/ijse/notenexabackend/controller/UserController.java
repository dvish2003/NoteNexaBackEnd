package lk.ijse.notenexabackend.controller;


import lk.ijse.notenexabackend.dto.AuthDTO;
import lk.ijse.notenexabackend.dto.ResponseDTO;
import lk.ijse.notenexabackend.dto.UserDTO;
import lk.ijse.notenexabackend.dto.VerifyUser;
import lk.ijse.notenexabackend.repo.UserRepository;
import lk.ijse.notenexabackend.service.custom.UserService;
import lk.ijse.notenexabackend.util.JwtUtil;
import lk.ijse.notenexabackend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    //constructor injection
    public UserController(UserService userService, UserRepository userRepository, JwtUtil jwtUtil) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("test");
        return "test";
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody UserDTO userDTO) {
        System.out.println("register");
        try {
            System.out.println(userDTO);
            String Role = "user";
            userDTO.setRole(Role);
            int res = userService.saveUser(userDTO);
            switch (res) {
                case VarList.Created -> {
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setEmail(userDTO.getEmail());
                    authDTO.setToken(token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }



    @PostMapping(value = "/verifyUser")
    public ResponseEntity<ResponseDTO> VerifiedUser(@RequestBody VerifyUser verifyUser) {
        System.out.println("Verified User"+verifyUser);
       try{
           int res = userService.verifyUser(verifyUser.getEmail(), verifyUser.getCode());
           switch (res) {
               case VarList.OK -> {
                   return ResponseEntity.status(HttpStatus.OK)
                           .body(new ResponseDTO(VarList.OK, "User Verified", verifyUser));
               }
               case VarList.Not_Found -> {
                   return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                           .body(new ResponseDTO(VarList.Not_Acceptable, "Code Is Wrong", null));
               }
               default -> {
                   return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                           .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
               }
           }
       }catch (Exception e) {
           System.out.println("Exception"+e.getMessage());
       }

        return null;
    }

    @GetMapping("/getUser")
    public ResponseEntity<ResponseDTO> getUser(@RequestParam String email) {
        System.out.println("Get User Use " + email);
        UserDTO userDTO = userService.searchUser(email);
        System.out.println("User id"+userDTO.getId());
        if (userDTO == null) {
            System.out.println("User not found ");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
        }
        return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Success", userDTO));
    }

        }
