package com.project.queryservice;

import com.project.dao.UserView;

import com.project.repository.UserRepository;
import com.project.utils.DaoToEntityMapper;
import com.project.utils.EntityToDaoMapper;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserQueryService {

    private final UserRepository userRepository;

    @Autowired
    public UserQueryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserView> fetchUserByEmailId(String emailId){
        return userRepository.findByEmail(emailId).map(EntityToDaoMapper::convertUserToUserView);
    }

    @Transactional
    public Optional<UserView> createUser(UserView userView) {
        userRepository.save(DaoToEntityMapper.convertUserViewToUser(userView));
        return Optional.of(userView);
    }
}
