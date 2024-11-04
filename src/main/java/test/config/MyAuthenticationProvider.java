package test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationProvider.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("Entering MyAuthenticationProvider.authenticate()");

        if (authentication.getPrincipal() == null || authentication.getCredentials() == null) {
            throw new BadCredentialsException("Username or Password empty");
        }

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            logger.info("User {} not existed", username);
            throw new BadCredentialsException("User not found");
        }

        // Authenticate
        String passwordHash = CommonUtils.md5(password); // Ensure CommonUtils.md5 method exists and is correct
        if (!passwordHash.equals(userDetails.getPassword())) {
            logger.info("User {} has entered an invalid password", username);
            throw new BadCredentialsException("Invalid password");
        }

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                userDetails, authentication.getCredentials(), userDetails.getAuthorities());

        logger.info("Leaving MyAuthenticationProvider.authenticate()");
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
