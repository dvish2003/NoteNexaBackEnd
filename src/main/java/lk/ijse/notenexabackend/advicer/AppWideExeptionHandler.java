package lk.ijse.notenexabackend.advicer;

import lk.ijse.notenexabackend.dto.ResponseDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWideExeptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseDTO ExeptionHandler(Exception e){
        return new ResponseDTO(500, "Internal Server Error", e.getMessage());
    }
}
