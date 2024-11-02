package com.sockrat.blogplatform.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "description")
    String description;
    @Column(name = "image", columnDefinition="bytea")
    byte[] image;
    @OneToMany(mappedBy = "post")
    List<Comment> comments;
    @Column(name = "author")
    String author;


    public Post() {
    }



    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Post(String description, byte[] image) {
        this.description = description;
        this.image = image;
    }

    public Post(String description, byte[] image, String author) {
        this.description = description;
        this.image = image;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
