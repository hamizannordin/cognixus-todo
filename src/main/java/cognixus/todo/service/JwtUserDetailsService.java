package cognixus.todo.service;

import cognixus.todo.repository.UserRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author hamizan
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        cognixus.todo.entity.User userEntity = userRepository.findByName(username);
        
        if (userEntity.getName().equals(username)) {
            return new User(userEntity.getName(), 
                    // for google sign in set password to empty string
                    userEntity.getPassword() != null ? userEntity.getPassword() : "",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
