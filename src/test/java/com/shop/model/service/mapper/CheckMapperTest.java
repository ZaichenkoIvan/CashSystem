package com.shop.model.service.mapper;

import com.shop.model.domain.Check;
import com.shop.model.domain.User;
import com.shop.model.entity.CheckEntity;
import com.shop.model.entity.UserEntity;
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
@ContextConfiguration(classes = {CheckMapper.class})
public class CheckMapperTest {
    private static final CheckEntity CHECK_ENTITY = getCheckEntity();

    private static final Check CHECK_DOMAIN = getCheck();

    private static final UserEntity USER_ENTITY = getUserEntity();

    private static final User USER_DOMAIN = getUser();

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private CheckMapper checkMapper;

    @After
    public void resetMock() {
        reset(userMapper);
    }

    @Test
    public void shouldMapCheckEntityToCheck() {
        when(userMapper.userEntityToUser(any(UserEntity.class))).thenReturn(USER_DOMAIN);

        Check actual = checkMapper.checkEntityToCheck(CHECK_ENTITY);

        assertThat(actual.getId(), is(CHECK_DOMAIN.getId()));
        assertThat(actual.getTotal(), is(CHECK_DOMAIN.getTotal()));
        assertThat(actual.getCanceled(), is(CHECK_DOMAIN.getCanceled()));
        assertThat(actual.getCrtime(), is(CHECK_DOMAIN.getCrtime()));
        assertThat(actual.getDiscount(), is(CHECK_DOMAIN.getDiscount()));
        assertThat(actual.getCreator(), is(CHECK_DOMAIN.getCreator()));
        assertThat(actual.getRegistration(), is(CHECK_DOMAIN.getRegistration()));
    }

    @Test
    public void shouldMapCheckToCheckEntity() {
        when(userMapper.userToUserEntity(any(User.class))).thenReturn(USER_ENTITY);

        CheckEntity actual = checkMapper.checkToCheckEntity(CHECK_DOMAIN);

        assertThat(actual.getId(), is(CHECK_ENTITY.getId()));
        assertThat(actual.getTotal(), is(CHECK_ENTITY.getTotal()));
        assertThat(actual.getCanceled(), is(CHECK_ENTITY.getCanceled()));
        assertThat(actual.getCrtime(), is(CHECK_ENTITY.getCrtime()));
        assertThat(actual.getDiscount(), is(CHECK_ENTITY.getDiscount()));
        assertThat(actual.getCreator(), is(CHECK_ENTITY.getCreator()));
        assertThat(actual.getRegistration(), is(CHECK_ENTITY.getRegistration()));
    }

    @Test
    public void mapCheckToCheckEntityShouldReturnNull() {
        CheckEntity actual = checkMapper.checkToCheckEntity(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void mapCheckEntityToCheckShouldReturnNull() {
        Check actual = checkMapper.checkEntityToCheck(null);

        assertThat(actual, is(nullValue()));
    }

    private static User getUser() {
        return User.builder()
                .id(1L)
                .email("email@gmail.com")
                .password("password")
                .name("name")
                .build();
    }

    private static UserEntity getUserEntity() {
        return new UserEntity(1L, "email@gmail.com", "password",
                "name", null, null);
    }

    private static CheckEntity getCheckEntity() {
        return new CheckEntity(1L, null, 100.0, 0.75,
                1, 1, null, USER_ENTITY);
    }

    private static Check getCheck() {
        return Check.builder()
                .id(1L)
                .total(100.0)
                .discount(0.75)
                .canceled(1)
                .registration(1)
                .creator(USER_DOMAIN)
                .build();
    }
}
