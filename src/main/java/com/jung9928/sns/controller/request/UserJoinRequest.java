package com.jung9928.sns.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// 회원가입 시, request body로 데이터를 받는 용도의 클래스
public class UserJoinRequest {

    private String name;
    private String password;
}
