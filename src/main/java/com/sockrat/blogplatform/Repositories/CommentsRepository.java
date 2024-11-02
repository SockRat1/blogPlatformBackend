package com.sockrat.blogplatform.Repositories;

import com.sockrat.blogplatform.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {

}
