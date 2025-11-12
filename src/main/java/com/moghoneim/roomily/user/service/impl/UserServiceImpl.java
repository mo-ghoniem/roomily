package com.moghoneim.roomily.user.service.impl;

import com.moghoneim.roomily.exception.UnauthenticatedUserException;
import com.moghoneim.roomily.role.dto.mapper.RoleMapper;
import com.moghoneim.roomily.role.entity.Role;
import com.moghoneim.roomily.role.repository.RoleRepository;
import com.moghoneim.roomily.security.CustomUserDetails;
import com.moghoneim.roomily.security.RoleType;
import com.moghoneim.roomily.user.dto.UpdateUserRequest;
import com.moghoneim.roomily.user.dto.UserDto;
import com.moghoneim.roomily.user.entity.User;
import com.moghoneim.roomily.user.exception.DatabaseException;
import com.moghoneim.roomily.user.exception.UserNotFoundException;
import com.moghoneim.roomily.user.dto.mapper.UserMapper;
import com.moghoneim.roomily.user.repository.UserRepository;
import com.moghoneim.roomily.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public List<UserDto> getAll() throws UserNotFoundException {
        try {
            List<User> users = userRepository.findAll();

            if (users.isEmpty()) {
                throw new UserNotFoundException("No users found in the system");
            }

            // Create a new list for DTOs to avoid modifying the original collection
            return users.stream().map(userMapper::toDto).toList();
        } catch (DataAccessException ex) {
            throw new DatabaseException("Error accessing the database: " + ex.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        userRepository.save(user);
    }

    @Override
    public void assignHostRole(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        if(user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleType.HOST))){
            throw new IllegalArgumentException("User is already a HOST.");
        }


        // Fetch HOST role from DB
        Role hostRole = roleRepository.findByName(RoleType.HOST)
                .orElseThrow(() -> new IllegalArgumentException("HOST role not found in DB"));

        // Assign role to user
        user.getRoles().add(hostRole);

        // Save updated user
        userRepository.save(user);

    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: \" + id"));
    }

    @Override
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            Long userId = customUserDetails.getId();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

            return userMapper.toDto(user);
        }

        throw new UnauthenticatedUserException("No authenticated user found");
    }



}
