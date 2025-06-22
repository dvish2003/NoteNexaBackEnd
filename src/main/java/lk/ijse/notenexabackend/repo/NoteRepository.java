package lk.ijse.notenexabackend.repo;

import lk.ijse.notenexabackend.Entity.Note;
import lk.ijse.notenexabackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    List<Note> findByUser(User user);
}
