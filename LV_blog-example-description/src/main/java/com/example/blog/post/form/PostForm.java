package com.example.blog.post.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostForm {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
