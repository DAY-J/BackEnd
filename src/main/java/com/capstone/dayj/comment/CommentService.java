package com.capstone.dayj.comment;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.post.Post;
import com.capstone.dayj.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final AppUserRepository appUserRepository;
    private final PostRepository postRepository;

    @Transactional
    public void createComment (int postId, int userId, CommentDto.Request dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomException(ErrorCode.POST_NOT_FOUND));
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));

        dto.setAppUser(user);
        dto.setPost(post);

        commentRepository.save(dto.toEntity());
    }

    @Transactional(readOnly = true)
    public List<CommentDto.Response> readAllComment(int postId) {

         Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomException(ErrorCode.POST_NOT_FOUND));

        List<Comment> comments = post.getComment();

        return comments.stream().map(CommentDto.Response::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentDto.Response readCommentById(int postId, int commentId){
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId)
                .orElseThrow(()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        return new CommentDto.Response(comment);
    }
    @Transactional
    public void patchComment(int postId, int commentId, CommentDto.Request dto){
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId)
                .orElseThrow(()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        comment.update(dto.getContent(), dto.isCommentIsAnonymous());
    } // 작성자 본인만 수정 가능

    @Transactional
    public void deleteCommentById(int postId, int commentId){
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId)
                .orElseThrow(()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        commentRepository.delete(comment);
    } // 작성자 본인만 삭제 가능
}


