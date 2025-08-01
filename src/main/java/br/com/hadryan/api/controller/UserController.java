package br.com.hadryan.api.controller;

import br.com.hadryan.api.mapper.UserMapper;
import br.com.hadryan.api.mapper.request.UserPostRequest;
import br.com.hadryan.api.mapper.response.UserResponse;
import br.com.hadryan.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        var userFound = userService.findById(id);
        return ResponseEntity.ok(userMapper.userToResponse(userFound));
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserPostRequest request){
        var userToSave = userMapper.requestToUser(request);
        var userSaved = userService.createUser(userToSave);
        return ResponseEntity
                .created(URI.create("/api/v1/users/" +  userSaved.getId()))
                .body(userMapper.userToResponse(userSaved));
    }

}
