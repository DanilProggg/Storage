package com.kridan.storage.auth.adapter.out.persistence;

import com.fasterxml.uuid.Generators;
import com.kridan.storage.auth.application.domain.exceptions.UserNotFoundException;
import com.kridan.storage.auth.application.domain.model.User;
import com.kridan.storage.auth.application.port.out.ExternalUserStorage;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPersistenceAdapter implements ExternalUserStorage {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoderAdapter bCryptPasswordEncoderAdapter;
    @Override
    public boolean createUser(String login, String password) {
        UserEntity user = new UserEntity()
                .setId(Generators.timeBasedGenerator().generate())
                .setLogin(login)
                .setPassword(bCryptPasswordEncoderAdapter.hashPassword(password));
        userRepository.save(user);
        return true;
    }

    @Override
    public User loginUser(String login, String password) {
        UserEntity user = userRepository.findByLogin(login).orElseThrow(()->new UserNotFoundException("User not found"));
        if (bCryptPasswordEncoderAdapter.checkPassword(password, user.getPassword())){
            return UserEntityMapper.toDomain(user);
        } else {
            return null;
        }
    }

}
