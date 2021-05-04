package com.shop.model.service.impl;

import com.shop.model.domain.User;
import com.shop.model.domain.UserType;
import com.shop.model.entity.UserEntity;
import com.shop.model.entity.UserTypeEntity;
import com.shop.model.exception.EntityNotFoundRuntimeException;
import com.shop.model.exception.InvalidDataRuntimeException;
import com.shop.model.repositories.UserRepository;
import com.shop.model.repositories.UserTypeRepository;
import com.shop.model.service.mapper.UserMapper;
import com.shop.model.service.mapper.UserTypeMapper;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {UserServiceImpl.class})
public class UserServiceImplTest {

    private static final UserType USER_TYPE = getUserType();
    private static final UserTypeEntity USER_TYPE_ENTITY = getUserTypeEntity();
    private static final User USER = getUser();
    private static final UserEntity ENTITY = getUserEntity();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @MockBean
    private UserRepository repository;

    @MockBean
    private UserTypeRepository userTypeRepository;

    @MockBean
    private UserMapper mapper;

    @MockBean
    private UserTypeMapper userTypeMapper;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserServiceImpl service;

    @After
    public void resetMock() {
        reset(repository, mapper);
    }

    @Test
    public void registrationClientShouldThrowUnCorrectInvalidDataRuntimeExceptionWithEmptyUser() {
        exception.expect(InvalidDataRuntimeException.class);
        exception.expectMessage("User is null");
        service.registration(null);
    }

    @Test
    public void registateUserShouldSaveUser() {
        when(encoder.encode(anyString())).thenReturn(USER.getPassword());
        when(userTypeMapper.userTypeEntityToUserType(any(UserTypeEntity.class))).thenReturn(USER_TYPE);
        when(userTypeRepository.findByType(anyString())).thenReturn(Optional.of(USER_TYPE_ENTITY));
        when(mapper.userToUserEntity(any(User.class))).thenReturn(ENTITY);
        when(repository.save(any(UserEntity.class))).thenReturn(ENTITY);

        User actual = new User(1L, "email@gmail.com", "pass", null, null, null);
        service.registration(actual);
        USER.setPassword(actual.getPassword());

        assertThat(actual, is(USER));
    }

    @Test
    public void loadUserByUsernameShouldThrowUnCorrectInputDataRuntimeExceptionWithEmptyLogin() {
        exception.expect(EntityNotFoundRuntimeException.class);

        service.loadUserByUsername(null);
    }

    @Test
    public void loadUserByUsernameShouldReturnRegisteredClient() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(ENTITY));
        when(mapper.userEntityToUser(any(UserEntity.class))).thenReturn(USER);

        UserDetails actual = service.loadUserByUsername("email@gmail.com");

        assertThat(actual, is(USER));
    }

    private static UserEntity getUserEntity() {
        return UserEntity
                .builder()
                .email("email@gmail.com")
                .build();
    }

    private static User getUser() {
        return User.builder()
                .id(1L)
                .email("email@gmail.com")
                .userType(USER_TYPE)
                .build();
    }

    private static UserTypeEntity getUserTypeEntity() {
        return new UserTypeEntity(2L, "cashier", "cashier", null);
    }

    private static UserType getUserType() {
        return UserType.builder()
                .id(2L)
                .type("cashier")
                .description("cashier")
                .build();
    }
}
