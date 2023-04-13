package com.project.beautysalonreservationapi.security.config;

import com.project.beautysalonreservationapi.security.services.JwtTokenService;
import com.project.beautysalonreservationapi.security.services.JwtUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService _jwtService;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        //check if the request header contains JWT token
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorization == null || !authorization.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        //extract token
        String token = authorization.substring(7);

        //get user info: username = email in this case
        String username =  _jwtService.getUsername(token);
        if(username!= null && SecurityContextHolder.getContext().getAuthentication()==null){//the token has username and this user hasnt been validated
            //get the user with given username from db
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            if(!_jwtService.isTokenExpired(token)){
                //set user in security context
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
