package com.example.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration //config 설정과 관련된 파일이니까 얘로 등록!
@EnableWebSecurity //사용하겠다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override //회원 인증과 관련된 부분을 구현할 수 있음
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() //http로 들어오는 요청들의 인증을 검사한다는 것을 명시
                .mvcMatchers("/", "/login","/sign-up").permitAll()
                //그 중에서 이 url들을 가지고 뭔가를 하겠다! 퍼밋 올 하겠다! 퍼미션을 다 연다는거임, 그래서 얘네는 인증을 하지 않겠다.
                //루트, 로그인, 회원가입 페이지 들어갈때 인증하면 서비스 이용 못하니까 인증 하지 않는다는거임.
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login").permitAll(); //로그인 페이지는 /login 이 url 로 하겠다.

        http.logout()
                .logoutUrl("/logout") //로그아웃 url 은 얘로 할꺼고 성공하면
                .logoutSuccessUrl("/"); //이 루트 페이지로 보내겠다!
    }
}
