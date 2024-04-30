package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.Comment;
import com.codingrecipe.member.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


}
