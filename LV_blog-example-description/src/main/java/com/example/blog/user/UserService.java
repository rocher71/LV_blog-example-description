package com.example.blog.user;

import com.example.blog.user.form.SignUpForm;
import com.example.blog.user.form.UserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void update(Long userId, UserForm userForm) {
        User user = findById(userId);
        user.setName(userForm.getName());
        user.setType(userForm.getType());
    }

    public User createUser(SignUpForm signUpForm) { //그냥 이렇게 만으로는 부족, 암호화가 필요함
        String encodedPassword = passwordEncoder.encode(signUpForm.getPassword());

        User user = User.builder()
                .username(signUpForm.getUsername())
                .password(encodedPassword)
                .name(signUpForm.getName())
                .type("USER")
                .build();

        save(user);

        return null;
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
        //user를 찾아서 만약 없으면 runtimeexception 에러를 발생
    }

    @Override //얘를 이용해서 함수를 찾겠다!
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);

        return new UserAccount(user);
    }

    public void login(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( //토큰 만들어서
                new UserAccount(user), //유저 찾고
                passwordEncoder.encode(user.getPassword()),
                List.of(new SimpleGrantedAuthority((user.getType())))
        );
        SecurityContextHolder.getContext().setAuthentication(token); //로그인 정보 넣고!
        //이 과정을 securitycontextholder가 다 해주는거임.
    }
}
