package com.moghoneim.roomily.authentication.service.impl;

import com.moghoneim.roomily.role.entity.Role;
import com.moghoneim.roomily.authentication.service.AuthService;
import com.moghoneim.roomily.authentication.dto.AuthRequest;
import com.moghoneim.roomily.authentication.dto.AuthResponse;
import com.moghoneim.roomily.authentication.dto.RegisterRequest;
import com.moghoneim.roomily.role.repository.RoleRepository;
import com.moghoneim.roomily.user.entity.User;
import com.moghoneim.roomily.user.repository.UserRepository;
import com.moghoneim.roomily.security.CustomUserDetails;
import com.moghoneim.roomily.security.JwtUtil;
import com.moghoneim.roomily.security.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Override
    @Transactional  // This ensures the method runs within a transaction.
    public void register(RegisterRequest request) {
        // 1. Fetch the roles from the database to ensure they are loaded and managed by Hibernate.
        List<Role> userRoleTypes = new ArrayList<>();

        Role userRole = roleRepository.findByName(RoleType.USER)
                .orElseThrow(() -> new RuntimeException("User role not found"));

        userRoleTypes.add(userRole); // Add the USER role to the set of roles.

        // 2. Create a new user with the roles already fetched.
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(userRoleTypes)
                .build();

        // 3. Save the user entity, which will automatically update the roles.
        userRepository.save(user);  // Save the user with roles in a single transaction.
    }


    @Override
    public AuthResponse authenticate(AuthRequest request) throws Exception {
        System.out.println("this function is excuting");
        System.out.println(request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println(user.getId());
        System.out.println(user.getPassword());
        System.out.println(request.getPassword());
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        System.out.println("this is after if condition");
        UserDetails userDetails = new CustomUserDetails(user);
        Map<String, Object> extraClaims = Map.of(
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "roles", user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toList())
        );
        String token = jwtUtil.generateToken(extraClaims, userDetails);
        return AuthResponse.builder().token(token).build();
    }
}
