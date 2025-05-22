package com.example.jpashopdemo.service;

import com.example.jpashopdemo.domain.entity.entity.User;
import com.example.jpashopdemo.domain.repository.repository.UserRepository;
import com.example.jpashopdemo.dto.UserDto;
import com.example.jpashopdemo.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDto createUser(UserDto userDto) {
        userDto.setId(null);
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    // 모든 사용자 조회
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::toUserDto).toList();
    }

    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found! with id: " + id)
        );
        return userMapper.toUserDto(user);
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found! with email: " + email)
        );
        return userMapper.toUserDto(user);
    }

    // Update - 사용자 정보 수정
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found! with id: " + id);
        }

        //ID 값 설정하여 업데이트
        userDto.setId(id);
        User upadatedUser = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toUserDto(upadatedUser);
    }

    // Delete - 사용자 삭제
    @Transactional
    public void deleteById(Long id) {
        if(!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found! with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

}
