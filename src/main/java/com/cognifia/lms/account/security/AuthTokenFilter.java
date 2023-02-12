package com.cognifia.lms.account.security;

import com.cognifia.lms.account.security.token.AuthorizeException;
import com.cognifia.lms.account.security.token.dto.UserDTO;
import com.cognifia.lms.account.security.token.utility.JWTUtility;
import com.cognifia.lms.common.annotation.validation.errormessage.ErrorMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author James Liu
 * @date 02/01/2023 -- 5:18 PM
 */

@Slf4j
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtility jwtUtil;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws AuthorizeException, ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        logger.info("go through internal filter begin");
        String username = null;
        String jwt = null;
        UserDTO user = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                jwt = authorizationHeader.substring(7);
                user = jwtUtil.validatetoken(jwt) ;
            } catch (AuthorizeException ex){
                handleAuthorizationException(response, ex) ;
                return ;
            }
        }


        if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getEmailAddress());

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            } catch (AuthorizeException ex){
                handleAuthorizationException(response, ex) ;
                return ;
            }

        }
        logger.info("go through internal filter finish");
        chain.doFilter(request, response);
    }

    private void handleAuthorizationException(HttpServletResponse response, AuthorizeException ex) throws IOException {
        String error = ErrorMessage.toLocale(ex.getMessage()) ;
        //response.setContentType("application/json");
        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(error);
    }


}
