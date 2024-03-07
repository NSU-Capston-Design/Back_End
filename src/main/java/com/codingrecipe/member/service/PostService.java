package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.post.PostAllDTO;
import com.codingrecipe.member.dto.post.PostOneDTO;
import com.codingrecipe.member.dto.post.PostSaveDTO;
import com.codingrecipe.member.dto.post.RequestPostDTO;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.entity.Post;
import com.codingrecipe.member.repository.MemberRepository;
import com.codingrecipe.member.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시글 생성
     * @param postSaveDTO
     * @return
     */
    public String postSave(PostSaveDTO postSaveDTO){

        Optional<MemberEntity> byUserId = memberRepository.findByUserId(postSaveDTO.getUserId());
        if (byUserId.isEmpty()){
            return null;
        } else {
            Post post = new Post(postSaveDTO.getPostTitle(), postSaveDTO.getPostDetail(), byUserId.get());
            postRepository.save(post);
            return "ok";
        }
    }

    /**
     * 게시글리스트 불러오기
     */
    public List<PostAllDTO> postAll(){
        List<Post> all = postRepository.findAll();
        List<PostAllDTO> postAll = new ArrayList<>();
        for (Post post : all){
            PostAllDTO postAllDTO = new PostAllDTO(post);

            postAll.add(postAllDTO);
        }
        return postAll;
    }

    /**
     * 게시글 단일 조회
     */
    public PostOneDTO postOne(Long postId){
        Optional<Post> byId = postRepository.findById(postId);
        if (byId.isEmpty()){
            return null;
        } else {
            return new PostOneDTO(byId.get());
        }
    }

    /**
     * 게시글 수정
     */
    public String postModify(Long postId, RequestPostDTO requestPostDTO){
        Optional<Post> byId = Optional.ofNullable(postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not Found with id: " + postId)));
        if (byId.isEmpty()){
            return null;
        } else {
            Post post = byId.get();
            post.setPostTitle(requestPostDTO.getPostTitle());
            post.setPostDetail(requestPostDTO.getPostDetail());
            postRepository.save(post);
            return "ok";
        }
    }

    /**
     * 게시글 삭제
     */
    public String postDelete(Long postId){
        if(postRepository.findById(postId).isEmpty()){
            return null;
        } else {
            postRepository.deleteById(postId);
            return "ok";
        }
    }
}
