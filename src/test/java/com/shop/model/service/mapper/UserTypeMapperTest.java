package com.shop.model.service.mapper;

import com.shop.model.domain.UserType;
import com.shop.model.entity.UserTypeEntity;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserTypeMapperTest {
    private static final UserTypeEntity USER_TYPE_ENTITY = getUserTypeEntity();

    private static final UserType USER_TYPE_DOMAIN = getUserType();

    private final UserTypeMapper userTypeMapper = new UserTypeMapper();

    @Test
    public void shouldMapUserTypeEntityToUserType() {
        UserType actual = userTypeMapper.userTypeEntityToUserType(USER_TYPE_ENTITY);

        assertThat(actual.getId(), is(USER_TYPE_DOMAIN.getId()));
        assertThat(actual.getType(), is(USER_TYPE_DOMAIN.getType()));
        assertThat(actual.getDescription(), is(USER_TYPE_DOMAIN.getDescription()));
    }

    @Test
    public void shouldMapUserTypeToUserTypeEntity() {
        UserTypeEntity actual = userTypeMapper.userTypeToUserTypeEntity(USER_TYPE_DOMAIN);

        assertThat(actual.getId(), is(USER_TYPE_ENTITY.getId()));
        assertThat(actual.getType(), is(USER_TYPE_ENTITY.getType()));
        assertThat(actual.getDescription(), is(USER_TYPE_ENTITY.getDescription()));
    }

    @Test
    public void mapUserTypeToUserTypeEntityShouldReturnNull() {
        UserTypeEntity actual = userTypeMapper.userTypeToUserTypeEntity(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void mapUserTypeEntityToUserTypeShouldReturnNull() {
        UserType actual = userTypeMapper.userTypeEntityToUserType(null);

        assertThat(actual, is(nullValue()));
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
