package com.sockrat.blogplatform.Controller;

import com.sockrat.blogplatform.Models.Comment;
import com.sockrat.blogplatform.Models.CommentRequest;
import com.sockrat.blogplatform.Models.Post;
import com.sockrat.blogplatform.Models.User;
import com.sockrat.blogplatform.Services.CommentService;
import com.sockrat.blogplatform.Services.PostService;
import com.sockrat.blogplatform.Services.UserService;
import com.sockrat.blogplatform.dto.UserDTO;
import com.sockrat.blogplatform.util.JWTUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    ModelMapper modelMapper;
    UserService userService;
    JWTUtil jwtUtil;
    PostService postService;
    final CommentService commentService;

    public MainController(ModelMapper modelMapper, UserService userService, PostService postService, JWTUtil jwtUtil, CommentService commentService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.postService = postService;
        this.jwtUtil = jwtUtil;
        this.commentService = commentService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        User user = convertUserDTOtoUser(userDTO);
        String token;
        if(userService.login(user.getUsername(), user.getPassword())){
            token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body("Invalid username or password");
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        User user = convertUserDTOtoUser(userDTO);
        userService.register(user);
        return ResponseEntity.ok(jwtUtil.generateToken(user.getUsername()));
    }

    @GetMapping("/getaccount")
    public ResponseEntity<User> getAccount() {
        return ResponseEntity.ok(userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @PostMapping("/post")
    public ResponseEntity<String> uploadPost(@RequestParam("file") MultipartFile file, @RequestParam("description") String description) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file provided.");
        }

        try {
            byte[] bytes = file.getBytes();
            Post post = new Post(description, bytes, SecurityContextHolder.getContext().getAuthentication().getName());
            postService.save(post); // Сохраняем пост с изображением
            return ResponseEntity.ok("Post created with ID: " + post.getId()); // Возвращаем ID поста
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving the file: " + e.getMessage());
        }
    }

    @DeleteMapping("/removepost/{id}")
    public ResponseEntity<String> removePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok("Post deleted");
    }
    @DeleteMapping("/removecomment/{id}")
    public ResponseEntity<String> removeComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted");
    }


    @GetMapping("post/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Long id) {
        Post post = postService.findById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    @PostMapping("/addcomment")
    public ResponseEntity<String> addcomment(@RequestBody CommentRequest commentRequest) {
        try {
            Comment comment = new Comment();
            comment.setAuthor(SecurityContextHolder.getContext().getAuthentication().getName());
            comment.setText(commentRequest.getText());
            comment.setPost(postService.findById(commentRequest.getId()));
            commentService.saveComment(comment);
            return ResponseEntity.ok("Comment created");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/getallposts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.findAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/verify")
    public ResponseEntity<HttpStatus> verify() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User convertUserDTOtoUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
