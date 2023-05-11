package ru.kotiki.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kotiki.dto.UserDto;
import ru.kotiki.services.UserService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users")
    public ResponseEntity<Void> registerUser(@RequestBody UserDto userDto) throws URISyntaxException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        UserDto user = userService.save(userDto);

        return ResponseEntity.created(new URI("/api/users/" + user.getId())).build();
    }

    @GetMapping("/api/users/all")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
}
