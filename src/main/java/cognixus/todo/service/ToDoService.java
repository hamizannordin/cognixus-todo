
package cognixus.todo.service;

import cognixus.todo.body.request.CreateToDoRequest;
import cognixus.todo.body.request.UpdateToDoRequest;
import cognixus.todo.entity.ToDo;
import cognixus.todo.exception.BadRequestException;
import cognixus.todo.exception.NotFoundException;
import cognixus.todo.repository.ToDoRepository;
import java.util.List;
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
        
        todo = todoRepository.save(todo);
        return todo;
    }

    public ToDo updateToDo(UpdateToDoRequest updateToDoRequest) {
        
        ToDo todo = todoRepository.getReferenceById(updateToDoRequest.getId());
        
        if(todo == null)
            throw new NotFoundException("ToDo id not found");
        
        todo.setCompleted(updateToDoRequest.isCompleted());
        
        log.info("update todo id: " + todo.getId().toString()
                + ", completed: " + todo.isCompleted());
        
        todo = todoRepository.save(todo);
        return todo;
    }
    
    public List<ToDo> listToDo() {
        
        List<ToDo> listToDo = todoRepository.findAll();
        
        if(listToDo == null || listToDo.isEmpty())
            throw new NotFoundException("ToDo list not found");
        
        log.info("list todo found: " + listToDo.size());
        return listToDo;
    }
}
