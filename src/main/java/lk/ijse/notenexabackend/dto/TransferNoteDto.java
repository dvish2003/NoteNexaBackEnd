package lk.ijse.notenexabackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransferNoteDto {
    private String email;
    private String topic;
    private String message;
    private NoteDTO note;
}
