
package cognixus.todo.repository;

import cognixus.todo.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hamizan
 */
@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Integer> {

}
