package com.example.blog.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //어느시점에, 즉 런타임시점에 여기 있는 기능을 추가할 것이다
@Target(ElementType.PARAMETER) //파라미터일 때 어노테이션 쓸거다
@AuthenticationPrincipal(expression = "#this == 'annonymousUser' ? null : user") //익명의 유저가 맞으면 null, 아니면 유저를 반환. 우리가 구현한 유저어카운트를 반환하는거임
public @interface CurrentUser {
}
