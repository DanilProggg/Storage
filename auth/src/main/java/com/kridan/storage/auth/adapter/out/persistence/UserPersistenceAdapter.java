package com.kridan.storage.auth.adapter.out.persistence;

import com.fasterxml.uuid.Generators;
import com.kridan.storage.auth.application.domain.exceptions.UserNotFoundException;
import com.kridan.storage.auth.application.domain.model.User;
import com.kridan.storage.auth.application.port.out.ExternalStorage;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPersistenceAdapter implements ExternalStorage {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public boolean createUser(String login, String password) {
        UserEntity user = new UserEntity()
                .setId(Generators.timeBasedGenerator().generate())
                .setLogin(login)
                .setPassword(hashPassword(password));
        userRepository.save(user);
        return true;
    }

    @Override
    public User loginUser(String login, String password) {
        UserEntity user = userRepository.findByLogin(login).orElseThrow(()->new UserNotFoundException("User not found"));
        if (checkPassword(password, user.getPassword())){
            return UserEntityMapper.toDomain(user);
        } else {
            return null;
        }
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
