package com.finaltask.networkofgiving.service;

import com.finaltask.networkofgiving.dto.UserDto;
import com.finaltask.networkofgiving.model.UsersTransaction;
import com.finaltask.networkofgiving.repository.UserRepository;
import com.finaltask.networkofgiving.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.finaltask.networkofgiving.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotAuthorizedException;
import java.util.Set;

@Service
public class CustomUserServices implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto getUserInfo() {
        org.springframework.security.core.userdetails.User loggedUser = authService.getCurrentUser().orElseThrow(
                () -> new NotAuthorizedException("User not found.")
        );

        User user = userRepository.findByUsername(loggedUser.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Not found info. "));
        return mapFromUserToDto(user);
    }

    @Override
    public UserDto editUserInformation(UserDto userDto) {
        org.springframework.security.core.userdetails.User loggedUser = authService.getCurrentUser().orElseThrow(
                () -> new NotAuthorizedException("Not Authorized.")
        );
        User user = userRepository.findByUsername(loggedUser.getUsername()).get();

        if(!user.getUsername().equals(loggedUser.getUsername())){
            throw new NotAuthorizedException("Not authorized.");
        }

        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAge(userDto.getAge());
        user.setGender(userDto.getGender());
        user.setLocation(userDto.getLocation());
        userRepository.save(user);
        return mapFromUserToDto(user);
    }

    @Override
    public Set<UsersTransaction> getUserTransaction() {
        org.springframework.security.core.userdetails.User loggedUser = authService.getCurrentUser().orElseThrow(
                () -> new NotAuthorizedException("Not Authorized.")
        );
        User user = userRepository.findByUsername(loggedUser.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("No user found " + loggedUser.getUsername()));

        return user.getUsersTransactions();
    }

    @Override
    public User mapFromDtoToUser(UserDto userDto) {
        org.springframework.security.core.userdetails.User loggedUser = authService.getCurrentUser().orElseThrow(
                () -> new NotAuthorizedException("User not found.")
        );
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setAge(userDto.getAge());
        user.setGender(userDto.getGender());
        user.setLocation(userDto.getLocation());
        user.setUsersTransactions(userDto.getUsersTransactions());
        return user;
    }

    @Override
    public UserDto mapFromUserToDto(User user){
        UserDto userDto =  new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        userDto.setAge(user.getAge());
        userDto.setGender(user.getGender());
        userDto.setLocation(user.getLocation());
        userDto.setUsersTransactions(user.getUsersTransactions());
        return userDto;
    }
}