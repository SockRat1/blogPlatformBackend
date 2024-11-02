package com.sockrat.blogplatform.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sockrat.blogplatform.Models.Post;
import com.sockrat.blogplatform.Repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public void save(Post post) {
        postRepository.save(post);
    }
    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }
    public List<Post> findAll() {
        return postRepository.findAll();
    }
    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
