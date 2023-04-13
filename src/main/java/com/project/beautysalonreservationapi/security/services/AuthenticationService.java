package com.project.beautysalonreservationapi.security.services;

import com.project.beautysalonreservationapi.security.DAL.UserRepository;
import com.project.beautysalonreservationapi.security.models.AuthenticationRequest;
import com.project.beautysalonreservationapi.security.models.AuthenticationResponse;
import com.project.beautysalonreservationapi.security.models.RegisterRequest;
import com.project.beautysalonreservationapi.security.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        try{
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        }
        catch(BadCredentialsException ex){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        //now user is authenticated, need to generate auth token for user
        UserDetails user = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenService.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();

    }
    public AuthenticationResponse register(RegisterRequest request){
        //create a new user from the request
        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        //save
        User savedUser =userRepository.save(user);
        //generate auth token;
        String token = jwtTokenService.generateToken(savedUser);

        return AuthenticationResponse.builder().token(token).build();
    }
}
