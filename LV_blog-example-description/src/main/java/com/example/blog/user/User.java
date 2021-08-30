package com.example.blog.user;

import com.example.blog.post.Post;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private String name;
    private String type;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;
}
