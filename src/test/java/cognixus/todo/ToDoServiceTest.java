package cognixus.todo;

import cognixus.todo.body.request.CreateToDoRequest;
import cognixus.todo.entity.ToDo;
import cognixus.todo.exception.BadRequestException;
import cognixus.todo.repository.ToDoRepository;
import cognixus.todo.service.ToDoService;
import static org.assertj.core.api.Assertions.*;
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
    int generatedIdCase02 = 1;
    
    @BeforeEach
    public void init () {
        
        todoRepository = Mockito.mock(ToDoRepository.class);
        
        // test-case 02 : createToDoSuccess
                
        ToDo todoCase02 = new ToDo();
        todoCase02.setTitle(titleCase02);
        todoCase02.setId(generatedIdCase02);
        
        when(todoRepository.save(any(ToDo.class))).thenReturn(todoCase02);
        
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
        
        assertThatThrownBy(()->todoService.createToDo(createToDoRequestTitleEmpty))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(messageCase00);
        assertThatThrownBy(()->todoService.createToDo(createToDoRequestTitleNull))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(messageCase00);
    }

    /**
     * test-case 01 : createToDoFailTitleCharacterLengthExceed
     */
    @Test
    public void createToDoFailTitleCharacterLengthExceed() {
        CreateToDoRequest createToDoRequest = new CreateToDoRequest();
        createToDoRequest.setTitle(titleCase01);
        assertThatThrownBy(()->todoService.createToDo(createToDoRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(messageCase01);
    }

    /**
     * test-case 02 : createToDoSuccess
     */
    @Test
    public void createToDoSuccess() {
        CreateToDoRequest createToDoRequest = new CreateToDoRequest();
        createToDoRequest.setTitle(titleCase02);
        assertThat(todoService.createToDo(createToDoRequest).getId() != null);
        assertThat(!todoService.createToDo(createToDoRequest).isCompleted());
    }

    /**
     * test-case 03 : updateToDoFailIdNotFound
     */
//    @Test
//    public void updateToDoFailIdNotFound() {
//    }

    /**
     * test-case 04 : updateToDoSuccess
     */
//    @Test
//    public void updateToDoSuccess() {
//    }

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
