package lk.ijse.notenexabackend.controller;


import lk.ijse.notenexabackend.dto.DeleteNote;
import lk.ijse.notenexabackend.dto.NoteDTO;
import lk.ijse.notenexabackend.dto.ResponseDTO;
import lk.ijse.notenexabackend.dto.TransferNoteDto;
import lk.ijse.notenexabackend.service.custom.NoteService;
import lk.ijse.notenexabackend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/note")
@CrossOrigin
public class NoteController {
@Autowired
private NoteService noteService;
    
    
    @PostMapping(value = "/saveNote")
    public ResponseEntity<ResponseDTO> saveNote(@RequestBody TransferNoteDto note) {
        System.out.println("not come from to Front end to Back end "+note);

        try{
            Date date = Date.valueOf(LocalDate.now());
            System.out.println(date);


            String topic = note.getTopic();
            String massage = note.getMessage();
            String email = note.getEmail();
            if(topic == null || massage == null || email == null) {
                System.out.println("topic or massage or email is null");
            }

            NoteDTO noteDTO = new NoteDTO();
            noteDTO.setTopic(topic);
            noteDTO.setMessage(massage);
            noteDTO.setDeleted(false);
            noteDTO.setDate(date);

            int res = noteService.saveNote(noteDTO, email);
            switch (res) {
                case VarList.Created -> {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", noteDTO));

                }
                case VarList.Not_Found ->{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "User not Found", null));

                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }



    @PutMapping(value = "/updateNote")
    public ResponseEntity<ResponseDTO> updateNote(@RequestBody TransferNoteDto note) {
        System.out.println("note come from to Front end to Back end for update ............ "+note);
     try{

         Date date = Date.valueOf(LocalDate.now());
         System.out.println(date);

         NoteDTO noteDTO = note.getNote();
         noteDTO.setTopic(note.getTopic());
         noteDTO.setMessage(note.getMessage());
         noteDTO.setDate(date);
         noteDTO.setDeleted(false);

         int res = noteService.updateNote(noteDTO, note.getEmail());
         switch (res) {
             case VarList.Created -> {
                 return ResponseEntity.status(HttpStatus.CREATED)
                         .body(new ResponseDTO(VarList.Created, "Success", noteDTO));

             }
             case VarList.Not_Found ->{
                 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                         .body(new ResponseDTO(VarList.Not_Found, "User not Found", null));

             }
             default -> {
                 return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                         .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
             }
         }
     }catch (Exception e){
         System.out.println(e.getMessage());
     }
        return null;
    }

    @PostMapping(value = "/deleteUseID")
    public ResponseEntity<ResponseDTO> deleteUseID(@RequestBody DeleteNote note) {
        System.out.println("delete note.......................");
          try{
              int res = noteService.deleteById(note.getId());
              switch (res){
                  case VarList.OK -> {
                      return ResponseEntity.status(HttpStatus.OK)
                              .body(new ResponseDTO(VarList.OK, "Success", note));

                  }
                  case VarList.Not_Found ->{
                      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                              .body(new ResponseDTO(VarList.Not_Found, "Note not Found", null));

                  }
                  default -> {
                      return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                              .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                  }
              }
          }catch (Exception e){
              System.out.println(e.getMessage());
          }
        return null;
    }

    @PostMapping(value = "/deleteUserEmail")
    public ResponseEntity<ResponseDTO> deleteUserEmail(@RequestBody TransferNoteDto note) {
        System.out.println("Delete Note By note id");
        return null;
    }
    @PostMapping(value = "/getUserNoteByEmail")
    public ResponseEntity<ResponseDTO> getAllUseByUSerEmail(@RequestBody TransferNoteDto note) {
        System.out.println("List");
        try{
            List<NoteDTO> noteDTOS = noteService.getAllNoteUserEmail(note.getEmail());
            System.out.println("address note list "+noteDTOS.getFirst());
            for (NoteDTO noteDTO : noteDTOS) {
                System.out.println("Note......."+noteDTO.isDeleted());
            }
            return new ResponseEntity<>(new ResponseDTO(VarList.OK, "note List", noteDTOS), HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(VarList.Internal_Server_Error, "Failed to get  List", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}


/* "note":{
      "id":"25ec489d-35c9-4bd2-b97b-a34ab6fde0bd",
      "topic":"Love with Vishan And Meedum",
      "message":"Vishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + MeedumVishan + Meedum Vishan + Meedum Vishan + Meedum"
    }*/