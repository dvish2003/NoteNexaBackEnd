package lk.ijse.notenexabackend.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
private ObjectId id;
    private String email;
    private String name;
    private String password;
    private String Role;

}
