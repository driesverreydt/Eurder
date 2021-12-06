package com.switchfully.projects.eurder.api.mapper;

import com.switchfully.projects.eurder.domain.user.User;
import com.switchfully.projects.eurder.domain.user.UserDto;
import com.switchfully.projects.eurder.security.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapUserDtoToUser(UserDto userDto) {
        return new User(userDto.getName(),
                userDto.getAddress(),
                userDto.getEmailAddress(),
                userDto.getPhoneNumber(),
                userDto.getUserRole());
    }

    public UserDto mapUserToUserDto(User user) {
        /*return new UserDto.UserDtoBuilder()
                .setUserId(user.getUserId())
                .setName(user.getName())
                .setAddress(user.getAddress())
                .setEmailAddress(user.getEmailAddress())
                .setPhoneNumber(user.getPhoneNumber())
                .setUserRole(user.getUserRole())
                .build();*
         */
        UserDto createUserCustomerDto = new UserDto();
        createUserCustomerDto.setUserId(user.getUserId());
        createUserCustomerDto.setName(user.getName());
        createUserCustomerDto.setAddress(user.getAddress());
        createUserCustomerDto.setEmailAddress(user.getEmailAddress());
        createUserCustomerDto.setPhoneNumber(user.getPhoneNumber());
        createUserCustomerDto.setUserRole(user.getUserRole());
        return createUserCustomerDto;
    }
}
