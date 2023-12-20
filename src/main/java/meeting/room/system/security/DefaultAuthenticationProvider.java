package meeting.room.system.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import meeting.room.system.security.model.UserDetails;

import java.util.Objects;

@Service
public class DefaultAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultAuthenticationProvider.class);

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }
    private String getUsername(Authentication authentication) {
        if (authentication == null) {
            return "";
        }
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Objects.requireNonNull(authentication, "Cannot authenticate: authentication is null!");

        if(authentication.isAuthenticated()){
            return SecurityContextHolder.getContext().getAuthentication();
        }
       // System.out.println("NAMEEEEEE : " +authentication.getName());


        final UserDetails userDetails = (UserDetails) userDetailsService.loadUserByUsername(authentication.getName());
        if(userDetails == null){
            throw new UsernameNotFoundException("User is not found.");
        }
        System.out.println("PASSED LOADBYUSERNAME !!!!!!!!!! : " +authentication.getName());

        Object creds = authentication.getCredentials();
        String password = creds == null ? null : creds.toString();

        if(passwordEncoder.matches(password, userDetails.getPassword())){
            userDetails.eraseCredentials();
            System.out.println("PASSED DEFAULT !!!!!!!!!! : " +authentication.getName()); //
            return SecurityUtils.setCurrentUser(userDetails);

        }
        else{
            throw new BadCredentialsException("Wrong password.");
        }

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass) ||
                AuthenticationToken.class.isAssignableFrom(aClass);
    }
}
