package com.sockrat.blogplatform.Models;


import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "author")
    private String author;
    @Column(name = "text")
    private String text;
    @ManyToOne()
    @JoinColumn(referencedColumnName = "id", name = "post")
    private Post post;

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public void setPost(Post post) {
        this.post = post;
    }
}
