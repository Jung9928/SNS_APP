package com.jung9928.sns.service;

import com.jung9928.sns.exception.ErrorCode;
import com.jung9928.sns.exception.SnsApplicationException;
import com.jung9928.sns.model.User;
import com.jung9928.sns.model.entity.UserEntity;
import com.jung9928.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;

    public User join(String userName, String password) {

        // 회원가입하려는 userName으로 회원가입된 user가 있는지
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", userName));
        });

        // 회원가입 진헹 = user를 등록
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, password));

        return User.fromEntity(userEntity);
    }

    public String login(String userName, String password) {
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

        // 비밀번호 체크
        if (!userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, "");
        }

        // 토큰 생성


        return "";
    }
}
