
package cognixus.todo.body.request;

/**
 *
 * @author hamizan
 */
public class UpdateToDoRequest {

    private int id;
    private boolean completed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
