package com.project.service;

import com.project.controllor.request.UserLoginRequest;
import com.project.controllor.request.UserRegistrationRequest;
import com.project.dao.UserView;
import com.project.queryservice.UserQueryService;
import com.project.response.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static com.project.response.Response.*;
import static com.project.utils.UserErrorMessages.*;
import static com.project.utils.UserMapper.convertRegistrationRequestToUserView;
import static com.project.utils.UserReadableMessages.LOGIN_SUCCESS;
import static com.project.utils.UserReadableMessages.REGISTER_SUCCESS;

@Service
@Log4j2
public class UserService {

    //todo : need to add the jwt token in this
    private final UserQueryService userQueryService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserQueryService userQueryService, PasswordEncoder passwordEncoder) {
        this.userQueryService = userQueryService;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<Response, Object> registerUser(UserRegistrationRequest userRegistrationRequest) {
        if (userQueryService.fetchUserByEmailId(userRegistrationRequest.getEmail()).isEmpty()) {
            return Map.of(ERROR, EMAIL_ALREADY_EXISTS,  ERROR_CODE, HttpStatus.CONFLICT);
        }
        userRegistrationRequest.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        Optional<UserView> userView =  userQueryService.createUser(
                        convertRegistrationRequestToUserView(userRegistrationRequest));
        if (userView.isEmpty()) {
            return Map.of(ERROR, REGISTER_FAILURE,  ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return Map.of(DATA, REGISTER_SUCCESS);
    }

    public Map<Response, Object> loginUser(UserLoginRequest userLoginRequest) {
        Optional<UserView> userView = userQueryService.fetchUserByEmailId(userLoginRequest.getEmail());
        if (userView.isEmpty()) {
            return Map.of(ERROR, EMAIL_NOT_EXISTS,  ERROR_CODE, HttpStatus.NOT_FOUND);
        }
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), userView.get().getPassword())) {
            return Map.of(ERROR, WRONG_PASSWORD,  ERROR_CODE, HttpStatus.UNAUTHORIZED);
        }
        return Map.of(DATA, LOGIN_SUCCESS);
    }

}
