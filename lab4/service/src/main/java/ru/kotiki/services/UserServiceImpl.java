package ru.kotiki.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kotiki.dao.UserRepository;
import ru.kotiki.dto.Mapper;
import ru.kotiki.dto.UserDto;
import ru.kotiki.entities.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDto findById(long id) {
        User user = userRepository.findById(id).orElse(null);
        return (user == null) ? null : mapper.toUserDto(user);
    }

    @Override
    public UserDto findByUsername(String username) {
        return mapper.toUserDto(userRepository.findByUsername(username));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto save(UserDto userDto) {
        return mapper.toUserDto(userRepository.save(mapper.toUser(userDto)));
    }
}
