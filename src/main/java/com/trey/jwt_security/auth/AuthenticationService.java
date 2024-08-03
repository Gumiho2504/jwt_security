package com.trey.jwt_security.auth;





import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trey.jwt_security.config.JwtService;
import com.trey.jwt_security.repository.UserRepository;
import com.trey.jwt_security.user.Role;
import com.trey.jwt_security.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationRespone register(RegisterRequest request) {
        var user = User.builder()
            .firstName(request.getFirstname())
            .lastName(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
       return AuthenticationRespone.builder()
            .token(jwtToken)
            .build();
    }

    public AuthenticationRespone authenticate(AuthenticationRequest request) {
       authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
       var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationRespone.builder()
                .token(jwtToken)
                .build();
      
    }
    
}
