package com.example.blog.user;

import com.example.blog.user.form.SignUpForm;
import com.example.blog.user.form.UserForm;
import com.example.blog.user.validator.UserFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserFormValidator userFormValidator;

    @InitBinder("userForm")
    public void initBinderUserForm(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userFormValidator);
    }

    //로그인 페이지
    @GetMapping("/login")
    public String login(){

        return "user/login";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model){
        model.addAttribute("signUpForm", new SignUpForm());

        return "user/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(SignUpForm signUpForm){
        User user = userService.createUser(signUpForm);
        userService.login(user);

        return "redirect:/";
    }

    // 유저 목록 조회
    // GET /users
    @GetMapping("/users")
    public String index(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        return "user/index";
    }

    // 유저 상세 조회
    // GET /users/1
    @GetMapping("/users/{userId}")
    public String show(@PathVariable Long userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);

        return "user/show";
    }

    @GetMapping("/new-user")
    public String newUser(Model model) {
        model.addAttribute("userForm", new UserForm());

        return "user/new";
    }

    // 유저 생성
    // POST /new-user
    @PostMapping("/new-user")
    public String create(@Valid UserForm userForm, Errors errors) {
        if (errors.hasErrors()) {
            return "user/new";
        }

//        User user = new User(
//                userForm.getId(),
//                userForm.getName(),
//                userForm.getType(),
//                new ArrayList<>()
//        );
//        userService.save(user);

        return "redirect:/users";
    }

    // 유저 수정
    // POST /users/{user_id}
    @GetMapping("/edit-user/{userId}")
    public String editUser(@PathVariable Long userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("userForm", new UserForm(
                user.getId(),
                user.getName(),
                user.getType()
        ));

        return "user/edit";
    }

    @PostMapping("/edit-user/{userId}")
    public String editUser(@PathVariable Long userId, UserForm userForm) {
        userService.update(userId, userForm);
        return "redirect:/users";
    }
}
