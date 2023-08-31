
package cognixus.todo.service;

import cognixus.todo.body.request.CreateToDoRequest;
import cognixus.todo.entity.ToDo;
import cognixus.todo.exception.BadRequestException;
import cognixus.todo.repository.ToDoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author hamizan
 */
@Service
public class ToDoService {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ToDoRepository todoRepository;

    public ToDoService (ToDoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public ToDo createToDo(CreateToDoRequest createToDoRequest) {
        
        if(createToDoRequest.getTitle() == null 
                || createToDoRequest.getTitle().isEmpty())
            throw new BadRequestException("Title null or empty");
        
        if(createToDoRequest.getTitle().length() > 50)
            throw new BadRequestException("Title exceeds character length");
        
        ToDo todo = new ToDo();
        todo.setTitle(createToDoRequest.getTitle());
        
        log.info("creating todo: " + todo.getTitle());
        
        return todoRepository.save(todo);
    }
}
