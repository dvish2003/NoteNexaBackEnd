package lk.ijse.notenexabackend.repo;


import com.vish.saratoga_backend.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User,String> {

    User findByEmail(String userName);

    boolean existsByEmail(String userName);

    int deleteByEmail(String userName);

   /* @Query(value = "SELECT * FROM users ORDER BY join_date DESC LIMIT 4", nativeQuery = true)
    List<User> findLast4MembersByJoinDate();*/
}