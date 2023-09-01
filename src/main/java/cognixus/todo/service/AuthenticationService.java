
package cognixus.todo.service;

import cognixus.todo.body.request.JwtRequest;
import cognixus.todo.body.response.JwtResponse;
import cognixus.todo.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author hamizan
 */
@Service
public class AuthenticationService {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    public JwtResponse authenticateUser (JwtRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(), 
                            authenticationRequest.getPassword()));
        } 
        catch (DisabledException e) {
            log.error("error", e);
            throw new RuntimeException("USER_DISABLED");
        } 
        catch (BadCredentialsException e) {
            log.error("error", e);
            throw new RuntimeException("INVALID_CREDENTIALS");
        }
        
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        
        return new JwtResponse(token);
    }
}
