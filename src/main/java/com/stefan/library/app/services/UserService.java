package com.stefan.library.app.services;

import com.stefan.library.app.exception.ResourceNotFoundException;
import com.stefan.library.app.models.ApplicationUser;
import com.stefan.library.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In the user details service");
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is invalid"));
    }
    @Transactional
    public void changePassword(Integer userId, String oldPassword, String newPassword)
            throws AuthenticationException {
        ApplicationUser user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AuthenticationException("Invalid old password");
        }
        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}
