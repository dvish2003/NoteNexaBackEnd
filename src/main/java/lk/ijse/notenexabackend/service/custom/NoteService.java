package lk.ijse.notenexabackend.service.custom;

import lk.ijse.notenexabackend.dto.NoteDTO;

import java.util.List;
import java.util.UUID;

public interface NoteService {
    int saveNote(NoteDTO noteDto, String email);

    int updateNote(NoteDTO noteDto, String email);

    int deleteById(UUID id);

    int deleteByEmailAll(String email);

    List<NoteDTO> getAllNoteUserEmail(String email);
}
