package com.shop.model.service.impl;

import com.shop.model.domain.User;
import com.shop.model.domain.UserType;
import com.shop.model.entity.UserEntity;
import com.shop.model.exception.EntityNotFoundRuntimeException;
import com.shop.model.exception.InvalidDataRuntimeException;
import com.shop.model.exception.UserIsAlsoExistRuntimeException;
import com.shop.model.repositories.UserRepository;
import com.shop.model.repositories.UserTypeRepository;
import com.shop.model.service.UserService;
import com.shop.model.service.mapper.UserMapper;
import com.shop.model.service.mapper.UserTypeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final UserMapper userMapper;
    private final UserTypeMapper userTypeMapper;

    @Override
    public User registration(User user) {
        if (Objects.isNull(user)) {
            log.warn("User is null");
            throw new InvalidDataRuntimeException("User is null");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.info("User is exist");
            throw new UserIsAlsoExistRuntimeException("User is exist");
        }
        UserType type = userTypeMapper.userTypeEntityToUserType(userTypeRepository.findByType("cashier")
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find user type by this type")));

        user.setPassword(encoder.encode(user.getPassword()));
        user.setUserType(type);
        UserEntity userEntity = userMapper.userToUserEntity(user);
        UserEntity saveEntity = userRepository.save(userEntity);

        return userMapper.userEntityToUser(saveEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        if (Objects.isNull(email)) {
            log.warn("Login is empty");
            throw new EntityNotFoundRuntimeException("Login is empty");
        }
        Optional<UserEntity> byLogin = userRepository.findByEmail(email);

        return byLogin.map(userMapper::userEntityToUser).orElse(null);
    }

    @Override
    public User getCurrentUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
