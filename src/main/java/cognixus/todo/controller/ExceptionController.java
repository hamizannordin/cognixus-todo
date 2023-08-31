
package cognixus.todo.controller;

import cognixus.todo.exception.BadRequestException;
import cognixus.todo.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author hamizan
 */
@ControllerAdvice
public class ExceptionController {
    
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> exception (BadRequestException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> exception (NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
