package com.stefan.library.app.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stefan.library.app.models.ApplicationUser;
import com.stefan.library.app.models.LoginResponseDTO;
import com.stefan.library.app.models.Role;
import com.stefan.library.app.repository.RoleRepository;
import com.stefan.library.app.repository.UserRepository;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public ApplicationUser registerUser(String username, String password) {
        // encode password
        String encodedPassword = passwordEncoder.encode(password);
        // assign USER role
        Role userRole = roleRepository.findByAuthority("USER").get();
        // make set of roles
        Set<Role> authorities = new HashSet<>();
        // add User to authorities set
        authorities.add(userRole);

        return userRepository.save(new ApplicationUser(0, username, encodedPassword, authorities));
    }

    public LoginResponseDTO loginUser(String username, String password) {

        try {
            // generate auth token
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            // generate JWT
            String token = tokenService.generateJwt(auth);
            // return user and token
            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "");
        }

    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
