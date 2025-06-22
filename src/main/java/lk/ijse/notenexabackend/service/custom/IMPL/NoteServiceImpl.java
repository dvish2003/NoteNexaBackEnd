package lk.ijse.notenexabackend.service.custom.IMPL;

import lk.ijse.notenexabackend.Entity.Note;
import lk.ijse.notenexabackend.Entity.User;
import lk.ijse.notenexabackend.dto.NoteDTO;
import lk.ijse.notenexabackend.repo.NoteRepository;
import lk.ijse.notenexabackend.repo.UserRepository;
import lk.ijse.notenexabackend.service.custom.NoteService;
import lk.ijse.notenexabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NoteRepository noteRepository;

 @Autowired
    private UserRepository userRepository;

@Override
public int saveNote(NoteDTO noteDto, String email) {
      try{
          User user = userRepository.findByEmail(email);
          System.out.println(user);

          if (user == null) {
              return VarList.Not_Found;
          }else{
              Note note = modelMapper.map(noteDto, Note.class);
              note.setUser(user);
              note.setDeleted(false);

              Note note1 = noteRepository.save(note);
              return VarList.Created;
          }
      }catch (Exception e){
          System.out.println(e.getMessage());
      }

        return 0;
    }

@Override
public int updateNote(NoteDTO noteDto, String email) {
        try{
            User user = userRepository.findByEmail(email);
            System.out.println("User........"+user);
            if (user == null) {
                return VarList.Not_Found;
            }
            Note note = modelMapper.map(noteDto, Note.class);
            note.setDeleted(false);
            note.setUser(user);
            UUID id = note.getId();
            if(noteRepository.existsById(id)){
                Note note1 = noteRepository.save(note);
                System.out.println(note1);
                return VarList.Created;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return 0;
    }

@Override
public int deleteById(UUID id){
      try{
          if(noteRepository.existsById(id)){
              Note note = noteRepository.findById(id).get();
              note.setDeleted(true);
              Note note1 = noteRepository.save(note);
              return VarList.OK;
          }
          return VarList.Not_Found;

      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
        return 0;
    }

@Override
public int deleteByEmailAll(String email){
    try{
        if(userRepository.existsByEmail(email)){
            User user = userRepository.findByEmail(email);
            UUID id = user.getId();

            List<Note> noteList = noteRepository.findByUser(user);
            for (Note note1 : noteList) {
                if(note1.isDeleted()){
                    noteRepository.delete(note1);
                    return VarList.OK;
                }
            }

        }
    }catch (Exception e){
        System.out.println(e.getMessage());
    }
        return 0;
    }


    @Override
    public List<NoteDTO> getAllNoteUserEmail(String email){
        System.out.println("get All .............");
     try{
         if(userRepository.existsByEmail(email)){
             System.out.println("Exist User........");
             List<Note> noteList = noteRepository.findAll();
             List<Note> noteList2 = new ArrayList<>();

             for (Note note : noteList){
                 if(note.getUser().getEmail().equals(email)){
                     System.out.println("service note" + note.isDeleted());
                     noteList2.add(note);
                 }else{
                     System.out.println("note equal note email");
                 }
             }

             for(Note note : noteList2){
                 System.out.println("service note 2 "+note.isDeleted());
             }

             System.out.println("dssssssssssssssssssssss"+noteList);
             return modelMapper.map(noteList2, new TypeToken<List<NoteDTO>>() {}.getType());
         }
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }

        return List.of();
    }





  /*
    public int tempDeleteNote(String noteID) {}

    public int DeleteAllNoteInTrash(String noteID) {}
*/
}
