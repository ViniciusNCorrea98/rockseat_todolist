package br.com.devvnc.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.devvnc.todolist.users.IUserRepository;
import jakarta.servlet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;

/** Component is the most generic of management classes on Spring*/
@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var auth = request.getHeader("Authorization");

        /** Getting just the auth in Base64
         * without the word 'Basic'
         * */
        auth.substring("Basic".length()).trim();

        byte [] authDecode = Base64.getDecoder().decode(auth);
        var authString = new String(authDecode);

        String [] credentials = authString.split(":");
        String username = credentials[0];
        String password = credentials[1];

        var user = this .userRepository.findByUsername(username);

        if(user == null){
            response.sendError(401);
        } else {
            var verify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

            if(verify.verified){
                filterChain.doFilter(request, response);
            } else {
                response.sendError(401);
            }

        }

    }
}
