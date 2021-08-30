package com.example.blog.user.validator;

import com.example.blog.user.UserRepository;
import com.example.blog.user.form.UserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserFormValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm userForm = (UserForm)target;

        if (userRepository.existsByName(userForm.getName())) {
            errors.rejectValue("name", "exists-value", "이미 존재하는 이름입니다.");
        }
    }
}
