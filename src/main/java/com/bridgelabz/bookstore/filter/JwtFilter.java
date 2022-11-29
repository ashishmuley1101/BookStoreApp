package com.bridgelabz.bookstore.filter;

import com.bridgelabz.bookstore.model.CustomUserDetails;
import com.bridgelabz.bookstore.service.CustomUserDetailsService;
import com.bridgelabz.bookstore.service.UserService;
import com.bridgelabz.bookstore.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public CustomUserDetailsService service;

    //

    /** doFilterInternalSame contract as for doFilter,
     but guaranteed to be just invoked once per request within a single request thread.
     **/
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        // "Authorization" is key for header of req.
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        String token = null;
        String userName = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            token = authorizationHeader.substring(7);

            //extractUsername(token) extract token and giving the userName
            userName = jwtUtil.extractUsername(token);
        }

        /**This means it is an Authentication which is typically an instance
         * of UsernamePasswordAuthenticationToken when using username and password based authentication**/
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = service.loadUserByUsername(userName);

            if (jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        //This doFilter() implementation stores a request attribute for already filtered.
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}