// ==================== CustomUserDetailsService.java ====================
package com.springapp.medicalapplication.security;

import com.springapp.medicalapplication.model.User;
import com.springapp.medicalapplication.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 CustomUserDetailsService - Loading user: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("❌ User not found in loadUserByUsername: " + username);
                    return new UsernameNotFoundException("User not found: " + username);
                });

        System.out.println("✅ User loaded: " + user.getUsername());
        System.out.println("📝 Password from DB: " + user.getPassword().substring(0, 20) + "...");
        System.out.println("🎭 Role: " + user.getRole().name());

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );

        System.out.println("🔑 Authorities: " + authorities);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    public User loadUserEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}