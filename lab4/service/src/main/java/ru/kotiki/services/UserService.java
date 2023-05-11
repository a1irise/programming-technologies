package ru.kotiki.services;

import ru.kotiki.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto findById(long id);

    List<UserDto> findAll();

    UserDto findByUsername(String username);


    UserDto save(UserDto userDto);
}
