package com.shop.model.service.mapper;

import com.shop.model.domain.User;
import com.shop.model.domain.UserType;
import com.shop.model.entity.UserEntity;
import com.shop.model.entity.UserTypeEntity;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {UserMapper.class})
public class UserMapperTest {
    private static final UserTypeEntity USER_TYPE_ENTITY = getUserTypeEntity();

    private static final UserType USER_TYPE_DOMAIN = getUserType();

    private static final UserEntity USER_ENTITY = getUserEntity();

    private static final User USER_DOMAIN = getUser();

    @MockBean
    private UserTypeMapper userTypeMapper;

    @Autowired
    private UserMapper userMapper;

    @After
    public void resetMock() {
        reset(userTypeMapper);
    }

    @Test
    public void shouldMapUserEntityToUser() {
        when(userTypeMapper.userTypeEntityToUserType(any(UserTypeEntity.class))).thenReturn(USER_TYPE_DOMAIN);

        User actual = userMapper.userEntityToUser(USER_ENTITY);

        assertThat(actual.getId(), is(USER_DOMAIN.getId()));
        assertThat(actual.getName(), is(USER_DOMAIN.getName()));
        assertThat(actual.getEmail(), is(USER_DOMAIN.getEmail()));
        assertThat(actual.getPassword(), is(USER_DOMAIN.getPassword()));
        assertThat(actual.getChecks(), is(USER_DOMAIN.getChecks()));
        assertThat(actual.getUserType(), is(USER_DOMAIN.getUserType()));
    }

    @Test
    public void shouldMapUserToUserEntity() {
        when(userTypeMapper.userTypeToUserTypeEntity(any(UserType.class))).thenReturn(USER_TYPE_ENTITY);

        UserEntity actual = userMapper.userToUserEntity(USER_DOMAIN);

        assertThat(actual.getId(), is(USER_ENTITY.getId()));
        assertThat(actual.getName(), is(USER_ENTITY.getName()));
        assertThat(actual.getEmail(), is(USER_ENTITY.getEmail()));
        assertThat(actual.getPassword(), is(USER_ENTITY.getPassword()));
        assertThat(actual.getChecks(), is(USER_ENTITY.getChecks()));
        assertThat(actual.getUserType(), is(USER_ENTITY.getUserType()));
    }

    @Test
    public void mapUserToUserEntityShouldReturnNull() {
        UserEntity actual = userMapper.userToUserEntity(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void mapUserEntityToUserShouldReturnNull() {
        User actual = userMapper.userEntityToUser(null);

        assertThat(actual, is(nullValue()));
    }

    private static UserEntity getUserEntity() {
        return new UserEntity(1L, "email@gmail.com", "password",
                "name", null, USER_TYPE_ENTITY);
    }

    private static User getUser() {
        return User.builder()
                .id(1L)
                .email("email@gmail.com")
                .password("password")
                .name("name")
                .userType(USER_TYPE_DOMAIN)
                .build();
    }

    private static UserTypeEntity getUserTypeEntity() {
        return new UserTypeEntity(1L, "cashier", "Cashier", null);
    }

    private static UserType getUserType() {
        return UserType.builder()
                .id(1L)
                .type("cashier")
                .description("Cashier")
                .build();
    }
}
