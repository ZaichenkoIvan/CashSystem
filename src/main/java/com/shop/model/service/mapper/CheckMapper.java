package com.shop.model.service.mapper;

import com.shop.model.domain.Check;
import com.shop.model.domain.User;
import com.shop.model.entity.CheckEntity;
import com.shop.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class CheckMapper {
    private UserMapper userMapper;

    public Check checkEntityToCheck(CheckEntity checkEntity) {
        if (Objects.isNull(checkEntity)) {
            return null;
        }

        User user = userMapper.userEntityToUser(checkEntity.getCreator());

        return Check.builder()
                .id(checkEntity.getId())
                .crtime(checkEntity.getCrtime())
                .total(checkEntity.getTotal())
                .discount(checkEntity.getDiscount())
                .canceled(checkEntity.getCanceled())
                .registration(checkEntity.getRegistration())
                .creator(user)
                .build();
    }

    public CheckEntity checkToCheckEntity(Check check) {
        if (Objects.isNull(check)) {
            return null;
        }

        UserEntity user = userMapper.userToUserEntity(check.getCreator());

        return CheckEntity.builder()
                .id(check.getId())
                .crtime(check.getCrtime())
                .total(check.getTotal())
                .discount(check.getDiscount())
                .canceled(check.getCanceled())
                .registration(check.getRegistration())
                .creator(user)
                .build();
    }
}
