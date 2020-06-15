package com.finaltask.networkofgiving.service.interfaces;

import com.finaltask.networkofgiving.dto.UserDto;
import com.finaltask.networkofgiving.model.User;
import com.finaltask.networkofgiving.model.UsersTransaction;

import java.util.Set;

public interface IUserService {

    UserDto editUserInformation(UserDto userDto);

    UserDto getUserInfo();
    Set<UsersTransaction> getUserTransaction();

    UserDto mapFromUserToDto(User user);
    User mapFromDtoToUser(UserDto userDto);
}
