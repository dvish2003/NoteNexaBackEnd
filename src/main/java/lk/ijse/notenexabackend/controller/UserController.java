package lk.ijse.notenexabackend.controller;


import com.vish.saratoga_backend.dto.AuthDTO;
import com.vish.saratoga_backend.dto.ResponseDTO;
import com.vish.saratoga_backend.dto.UserDTO;
import com.vish.saratoga_backend.repo.UserRepository;
import com.vish.saratoga_backend.service.custom.UserService;
import com.vish.saratoga_backend.util.JwtUtil;
import com.vish.saratoga_backend.util.VarList;
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
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody  UserDTO userDTO) {
        System.out.println("register");
        try {
            String Role = "User";
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

    /*@GetMapping("/get4Users")
    public ResponseEntity<ResponseDTO> getTop4Users() {
        System.out.println("get 4 Users");
      try{ List<UserDTO> users = userService.getLast4Users();
          if (users.isEmpty()) {
              return ResponseEntity.status(HttpStatus.NOT_FOUND)
                      .body(new ResponseDTO(VarList.Not_Found, "Users Not Found", null));
          }else{
              return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Success", users));

          }} catch (Exception e) {
          throw new RuntimeException(e);
      }
    }

    @GetMapping("/getProfilePic/{userEmail}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable String userEmail) {
        User user = userRepository.findByEmail(String.valueOf(userEmail));
        byte[] imageData = user.getData();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }

    @GetMapping("/getUser")
    public ResponseEntity<ResponseDTO> getUser(@RequestParam String email) {
        System.out.println("Get User Use " + email);
        UserDTO userDTO = userService.searchUser(email);
        System.out.println("ndsfisjdifsn"+userDTO.getUid());
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
        }
        return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Success", userDTO));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO) {
        System.out.println("register");
        System.out.println(userDTO.getEmail());
        System.out.println(userDTO.getName());
        System.out.println(userDTO.getRole());
        try {
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

    @PutMapping(value = "/updateUser2")
    public ResponseEntity<ResponseDTO> updateUser2(@RequestParam String email, @RequestBody @Valid UserDTO userDTO) {
        try{
            int res = userService.updateUser2(userDTO);
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
            throw new RuntimeException(e);
        }
    }
    @PutMapping(value = "/updateUser")
    public ResponseEntity<ResponseDTO> updateUser(
            @RequestPart("userDTO") @Valid UserDTO userDTO,
            @RequestParam("file") MultipartFile file) {

        System.out.println("Update User: " + userDTO.getEmail());

        try {
            if (file != null && !file.isEmpty()) {
                Path uploadDir = Paths.get(System.getProperty("user.dir") + "/uploads/");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                Path filePath = uploadDir.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                userDTO.setProfilePicture(fileName);
            }
            //get local date
            Date localDate = Date.valueOf(LocalDate.now());
            userDTO.setJoinDate(localDate);
            int res = userService.updateUser(userDTO, file);

            switch (res) {
                case VarList.OK:
                    System.out.println("User updated");
                    return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User Updated Successfully", null));
                case VarList.Not_Found:
                    System.out.println("User not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
                default:
                    System.out.println("Error updating user");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseDTO(VarList.Internal_Server_Error, "Error updating user", null));
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Internal Server Error", null));
        }
    }
    @PostMapping(value = "/updateUser3")
    public ResponseEntity<ResponseDTO> updateUser3(@RequestBody @Valid UserDTO userDTO) {
        try {
            int res = userService.updateUser(userDTO);
            switch (res) {
                case VarList.OK:
                    System.out.println("User updated");
                    return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User Updated Successfully", userDTO));
                case VarList.Not_Found:
                    System.out.println("User not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
                default:
                    System.out.println("Error updating user");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseDTO(VarList.Internal_Server_Error, "Error updating user", null));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

       }

       @PostMapping(value = "/deleteUser")
       public ResponseEntity<ResponseDTO> deleteUser(@RequestBody VerifyUserDTO verifyUserDTO) {
        try {
            int res = userService.deactiveAccount(verifyUserDTO);
            switch (res) {
                case VarList.OK:
                    System.out.println("User deleted");
                    return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User Deleted Successfully", verifyUserDTO));
                case VarList.Not_Found:
                    System.out.println("User not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
                default:
                    System.out.println("Error deleting user");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseDTO(VarList.Internal_Server_Error, "Error deleting user", null));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
       }

       @PostMapping(value = "/falseNullUser")
       public ResponseEntity<ResponseDTO> falseNullUser(@RequestBody VerifyUserDTO verifyUserDTO) {
        try {
            int res = userService.CodeSent(verifyUserDTO.getEmail());
            switch (res) {
                case VarList.OK:
                    System.out.println("User verified code sent");
                    return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User Verified Successfully", verifyUserDTO));
                case VarList.Not_Found:
                    System.out.println("User not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
                default:
                    System.out.println("Error verifying user");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseDTO(VarList.Internal_Server_Error, "Error verifying user", null));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
       }
       @GetMapping(value = "/getAllUsers")
       public ResponseEntity<ResponseDTO> getAllUsers() {
        try {
            List<UserDTO> users = userService.getUsers();
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "All Users", users));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(VarList.Internal_Server_Error, "Failed to get  List", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
       }*/
        }
