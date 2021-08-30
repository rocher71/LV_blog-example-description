package com.example.blog.post;

import com.example.blog.comment.Comment;
import com.example.blog.comment.CommentService;
import com.example.blog.comment.form.CommentForm;
import com.example.blog.post.form.PostForm;
import com.example.blog.user.CurrentUser;
import com.example.blog.user.User;
import com.example.blog.user.UserRepository;
import com.example.blog.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping("/posts")
    public String index(Model model) {
        model.addAttribute("posts", postService.findAll());

        return "post/index";
    }

    @GetMapping("/posts/{postId}")
    public String show(@PathVariable Long postId, Model model) {
        Post post = postService.findById(postId);
        model.addAttribute("post", post);
        model.addAttribute("commentForm", new CommentForm());

        return "post/show";
    }

    @GetMapping("/new-post")
    public String newPost(Model model) {
        model.addAttribute("postForm", new PostForm());

        return "post/new";
    }

    @PostMapping("/new-post")
    public String create(
            @CurrentUser User user,
            @Valid PostForm postForm,
            Errors errors)
    {
        if (errors.hasErrors()) {
            return "post/new";
        }


        postService.create(postForm, user);

        return "redirect:/posts";
    }

    @GetMapping("/edit-post/{postId}")
    public String edit(@PathVariable Long postId, Model model) {
        Post post = postService.findById(postId);
        model.addAttribute("post", post);

        PostForm postForm = new PostForm();
        postForm.setTitle(post.getTitle());
        postForm.setDescription(post.getDescription());
        model.addAttribute("postForm", postForm);

        return "post/edit";
    }

    @PostMapping("/edit-post/{postId}")
    public String edit(@PathVariable Long postId, PostForm postForm) {
        postService.update(postId, postForm);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{postId}/new-comment")
    public String create(@PathVariable Long postId, @Valid CommentForm commentForm) {
        Post post = postService.findById(postId);
        User user = userService.findById(1L);
        Comment comment = Comment.builder()
                .content(commentForm.getContent())
                .post(post)
                .user(user)
                .build();
        commentService.create(comment);

        return "redirect:/posts/" + post.getId();
    }
}
