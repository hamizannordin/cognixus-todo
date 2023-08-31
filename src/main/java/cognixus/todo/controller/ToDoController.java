
package cognixus.todo.controller;

import cognixus.todo.body.request.CreateToDoRequest;
import cognixus.todo.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hamizan
 */
@RestController
@RequestMapping("/todo")
public class ToDoController {

    @Autowired
    private ToDoService todoService;
    
    @RequestMapping(method = RequestMethod.POST, path = "/detail")
    public ResponseEntity<Object> createToDo (@RequestBody CreateToDoRequest request) {
        return new ResponseEntity<>(todoService.createToDo(request), HttpStatus.OK);
    }
}
