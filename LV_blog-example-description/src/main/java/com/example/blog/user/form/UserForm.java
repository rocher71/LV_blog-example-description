package com.example.blog.user.form;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String type;
}
