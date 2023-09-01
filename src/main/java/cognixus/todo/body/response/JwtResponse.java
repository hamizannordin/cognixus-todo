package cognixus.todo.body.response;

import java.io.Serializable;

/**
 *
 * @author hamizan
 */
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
