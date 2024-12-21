package com.project.utils;

import com.project.controllor.request.UserRegistrationRequest;
import com.project.dao.UserView;

public class UserMapper {

    public static UserView convertRegistrationRequestToUserView(UserRegistrationRequest userRegistrationRequest) {
        return UserView.builder()
                .name(userRegistrationRequest.getName())
                .email(userRegistrationRequest.getEmail())
                .password(userRegistrationRequest.getPassword())
                .build();
    }
}
