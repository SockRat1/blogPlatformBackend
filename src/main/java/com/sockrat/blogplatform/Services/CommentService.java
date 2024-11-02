package com.sockrat.blogplatform.Services;

import com.sockrat.blogplatform.Models.Comment;
import com.sockrat.blogplatform.Repositories.CommentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    final CommentsRepository commentsRepository;

    public CommentService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public void addComment(Comment comment) {
        commentsRepository.save(comment);
    }
    public List<Comment> getAllComments() {
        return commentsRepository.findAll();
    }
    public void saveComment(Comment comment) {
        commentsRepository.save(comment);
    }
    public void deleteComment(Long id) {
        commentsRepository.deleteById(id);
    }
}
