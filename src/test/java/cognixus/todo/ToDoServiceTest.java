package cognixus.todo;

import cognixus.todo.body.request.CreateToDoRequest;
import cognixus.todo.body.request.UpdateToDoRequest;
import cognixus.todo.entity.ToDo;
import cognixus.todo.exception.BadRequestException;
import cognixus.todo.exception.NotFoundException;
import cognixus.todo.repository.ToDoRepository;
import cognixus.todo.service.ToDoService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

/**
 * 
 * @author hamizan
 */
public class ToDoServiceTest {
    
    private ToDoService todoService;
    private ToDoRepository todoRepository;
    
    // test-case 00 : createToDoFailTitleNullOrEmpty
    String titleCase00Empty = "";
    String titleCase00Null = null;
    String messageCase00 = "Title null or empty";
    
    // test-case 01 : createToDoFailTitleCharacterLengthExceed - max length 50
    String titleCase01 = "T".repeat(51);
    String messageCase01 = "Title exceeds character length";
    
    // test-case 02 : createToDoSuccess
    String titleCase02 = "TEST";
    int generatedIdCase02 = 2;
    
    // test-case 03 : updateToDoFailIdNotFound
    int idCase03 = 3;
    boolean completedCase03 = true;
    String messageCase03 = "ToDo id not found";
    
    // test-case 04 : updateToDoSuccess
    String titleCase04 = "TEST";
    int idCase04 = 4;
    boolean completedCase04 = true;
    
    @BeforeEach
    public void init () {
        
        todoRepository = Mockito.mock(ToDoRepository.class);
        
        // test-case 02 : createToDoSuccess
                
        ToDo todoCase02 = new ToDo();
        todoCase02.setTitle(titleCase02);
        todoCase02.setId(generatedIdCase02);
        
        when(todoRepository.save(any(ToDo.class))).thenReturn(todoCase02);
        
        // test-case 04 : updateToDoSuccess
                
        ToDo todoCase04 = new ToDo();
        todoCase04.setTitle(titleCase04);
        todoCase04.setId(idCase04);
        todoCase04.setCompleted(completedCase04);
        
        when(todoRepository.getReferenceById(idCase04)).thenReturn(todoCase04);
        when(todoRepository.save(todoCase04)).thenReturn(todoCase04);
        
        todoService = new ToDoService(todoRepository);
    }

    /**
     * test-case 00 : createToDoFailTitleNullOrEmpty
     */
    @Test
    public void createToDoFailTitleNullOrEmpty() {
        CreateToDoRequest createToDoRequestTitleEmpty = new CreateToDoRequest();
        createToDoRequestTitleEmpty.setTitle(titleCase00Empty);
        CreateToDoRequest createToDoRequestTitleNull = new CreateToDoRequest();
        createToDoRequestTitleNull.setTitle(titleCase00Null);
        
        assertTrue(assertThrows(BadRequestException.class, 
                ()->{todoService.createToDo(createToDoRequestTitleEmpty);})
                .getMessage().equals(messageCase00));
        assertTrue(assertThrows(BadRequestException.class, 
                ()->{todoService.createToDo(createToDoRequestTitleNull);})
                .getMessage().equals(messageCase00));
    }

    /**
     * test-case 01 : createToDoFailTitleCharacterLengthExceed
     */
    @Test
    public void createToDoFailTitleCharacterLengthExceed() {
        CreateToDoRequest createToDoRequest = new CreateToDoRequest();
        createToDoRequest.setTitle(titleCase01);
        
        assertTrue(assertThrows(BadRequestException.class, 
                ()->{todoService.createToDo(createToDoRequest);})
                .getMessage().equals(messageCase01));
    }

    /**
     * test-case 02 : createToDoSuccess
     */
    @Test
    public void createToDoSuccess() {
        CreateToDoRequest createToDoRequest = new CreateToDoRequest();
        createToDoRequest.setTitle(titleCase02);
        
        assertTrue(todoService.createToDo(createToDoRequest).getId() != null);
        assertTrue(!todoService.createToDo(createToDoRequest).isCompleted());
    }

    /**
     * test-case 03 : updateToDoFailIdNotFound
     */
    @Test
    public void updateToDoFailIdNotFound() {
        UpdateToDoRequest updateToDoRequest = new UpdateToDoRequest();
        updateToDoRequest.setId(idCase03);
        updateToDoRequest.setCompleted(completedCase03);
        
        assertTrue(assertThrows(NotFoundException.class, 
                ()->{todoService.updateToDo(updateToDoRequest);})
                .getMessage().equals(messageCase03));
    }

    /**
     * test-case 04 : updateToDoSuccess
     */
    @Test
    public void updateToDoSuccess() {
        UpdateToDoRequest updateToDoRequest = new UpdateToDoRequest();
        updateToDoRequest.setId(idCase04);
        updateToDoRequest.setCompleted(completedCase04);
        
        assertTrue(todoService.updateToDo(updateToDoRequest).isCompleted() 
                == completedCase04);
    }

    /**
     * test-case 05 : listToDoSucess
     */
//    @Test
//    public void listToDoSucess() {
//    }
        
    /**
     * test-case 06 : deleteToDoFailIdNotFound
     */
//    @Test
//    public void deleteToDoFailIdNotFound() {
//    }

    /**
     * test-case 07 : deleteToDoSuccess
     */
//    @Test
//    public void deleteToDoSuccess() {
//    }
}
