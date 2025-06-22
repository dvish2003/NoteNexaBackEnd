package lk.ijse.notenexabackend.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    private boolean deleted;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
