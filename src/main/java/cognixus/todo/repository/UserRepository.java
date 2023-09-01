
package cognixus.todo.repository;

import cognixus.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hamizan
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    
    User findByName(String name);
}
