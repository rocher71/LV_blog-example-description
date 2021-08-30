package com.example.blog.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
public class UserAccount extends User {

    private com.example.blog.user.User user;

    public UserAccount(com.example.blog.user.User user) {
        super(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getType())));
        this.user = user;
    }

    //form에서 올라오는 username, password임
//    public UserAccount(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, authorities);
//        this.user = user;
//    }
}
