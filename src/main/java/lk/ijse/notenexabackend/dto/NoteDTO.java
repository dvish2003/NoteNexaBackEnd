package lk.ijse.notenexabackend.dto;

import lk.ijse.notenexabackend.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoteDTO {
        private UUID id;
        private String topic;
        private String message;
        private boolean deleted;
        private Date date;
        private User user;

}
