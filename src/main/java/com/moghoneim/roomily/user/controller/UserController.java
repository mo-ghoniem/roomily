package com.moghoneim.roomily.user.controller;

import com.moghoneim.roomily.user.dto.UpdateUserRequest;
import com.moghoneim.roomily.user.dto.UserDto;
import com.moghoneim.roomily.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> get(){
        List<UserDto> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request){
        userService.updateUser(id, request);
        return ResponseEntity.ok("User updated successfully.");
    }

    @PatchMapping("/{id}/become-host")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> becomeHost(@PathVariable Long id){
        userService.assignHostRole(id);
        return ResponseEntity.ok("User has been successfully upgraded to HOST.");
    }
}
