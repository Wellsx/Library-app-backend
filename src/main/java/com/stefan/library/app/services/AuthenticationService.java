package com.stefan.library.app.services;

import java.util.HashSet;
import java.util.Set;

import com.stefan.library.app.dto.RegistrationResponse;
import com.stefan.library.app.dto.AuthenticationRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stefan.library.app.models.ApplicationUser;
import com.stefan.library.app.dto.LoginResponse;
import com.stefan.library.app.models.Role;
import com.stefan.library.app.repository.RoleRepository;
import com.stefan.library.app.repository.UserRepository;

@Service
@Transactional
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public RegistrationResponse registerUser(AuthenticationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        ApplicationUser newUser = userRepository.save(new ApplicationUser(null, username, encodedPassword, authorities));

        RegistrationResponse response = new RegistrationResponse();
        response.setUserId(newUser.getUserId());
        response.setUsername(newUser.getUsername());
        return response;
    }
    public LoginResponse loginUser(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            String token = tokenService.generateJwt(auth);
        return new LoginResponse(userRepository.findByUsername(username).get(), token);
    }
}
