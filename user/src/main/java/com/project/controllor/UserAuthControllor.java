package com.project.controllor;

import com.project.controllor.request.UserLoginRequest;
import com.project.controllor.request.UserRegistrationRequest;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.project.utils.ResponseProcessorUtil.processResult;

@RestController
@RequestMapping("/user/auth")
public class UserAuthControllor {

        @Autowired
        private UserService userService;

        @PostMapping("/register")
        public ResponseEntity<?> register(@RequestBody UserRegistrationRequest userRegistrationRequest) {
            return processResult(userService.registerUser(userRegistrationRequest));
        }

        @GetMapping("/login")
        public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest) {
            return processResult(userService.loginUser(userLoginRequest));
        }
}
