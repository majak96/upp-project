package upp.project.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.camunda.bpm.engine.IdentityService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;

import upp.project.model.Authority;
import upp.project.model.RegisteredUser;
import upp.project.repositories.UserRepository;
import upp.project.services.UserService;
import upp.project.utils.SpringContext;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

	private UserRepository userRepository;
	
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        
    	//read the authorization header, where the JWT token should be
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        //if header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        //if header is present, try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        IdentityService identityService = SpringContext.getBean(IdentityService.class);
        UserService userService = SpringContext.getBean(UserService.class);
        
        //log in to camunda
        String username = authentication.getName();
        Authority authority = userService.findByUsername(username).getAuthority();
        String role = authority.getRole().getCamundaGroupName();
        	
		identityService.setAuthenticatedUserId(username);
		identityService.setAuthentication(username, Collections.singletonList(role));
		
        //continue filter execution
        chain.doFilter(request, response);
    }
    
    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX,"");

        if (token != null) {
            // parse the token and validate it
            String userName = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();

            // search if we find the user by token subject (username)
            if (userName != null) {
                RegisteredUser user = userRepository.findByUsername(userName);
                UserPrincipal principal = new UserPrincipal(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, null, principal.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }

}
