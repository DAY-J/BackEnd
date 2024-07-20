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
    public void createComment(int post_id, int app_user_id, CommentDto.Request dto) {
        Post findPost = postRepository.findById(post_id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        AppUser findAppUser = appUserRepository.findById(app_user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        
        dto.setPost(findPost); // 댓글 달 게시물
        dto.setAppUser(findAppUser); // 댓글 쓴 유저
        commentRepository.save(dto.toEntity());
    }
    
    @Transactional
    public void createReply(int post_id, int app_user_id, int comment_id, CommentDto.Request dto) {
        Post findPost = postRepository.findById(post_id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        AppUser findAppUser = appUserRepository.findById(app_user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        Comment findComment = findPost.getComment().stream()
                .filter(comment -> comment.getId() == comment_id).findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        
        dto.setPost(findComment.getPost()); // 대댓글을 작성한 게시물
        dto.setParentId(findComment.getId()); // 대댓글의 부모 댓글
        dto.setAppUser(findAppUser); // 대댓글 작성자
        commentRepository.save(dto.toEntity());
    }
    
    
    @Transactional(readOnly = true)
    public List<CommentDto.Response> readAllComment(int post_id) {
        Post findPost = postRepository.findById(post_id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        
        List<Comment> findComments = findPost.getComment();
        
        if (findComments.isEmpty()) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }
        
        return findComments.stream().map(CommentDto.Response::new).collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CommentDto.Response readCommentById(int post_id, int comment_id) {
        Post findPost = postRepository.findById(post_id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        List<Comment> findComments = findPost.getComment();
        
        if (findComments.isEmpty()) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }
        
        Comment findComment = findComments.stream()
                .filter(comment -> comment.getId() == comment_id).findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        
        return new CommentDto.Response(findComment);
    }
    
//    @Transactional(readOnly = true)
//    public List<CommentDto.Response> readAllReplyByCommentId(int post_id, int comment_id) {
//        Post findPost = postRepository.findById(post_id)
//                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
//        List<Comment> findComments = findPost.getComment();
//
//        if (findComments.isEmpty()) {
//            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
//        }
//
//        List<Comment> findReplies = findComments
//                .stream().filter(comment -> comment.getParentId() == comment_id).toList();
//
//        if (findReplies.isEmpty()) {
//            throw new CustomException(ErrorCode.REPLY_NOT_FOUND);
//        }
//
//        return findReplies.stream().map(CommentDto.Response::new).collect(Collectors.toList());
//    }
    
    
    @Transactional
    public void patchComment(int post_id, int comment_id, CommentDto.Request dto) {
        Comment comment = commentRepository.findByPostIdAndId(post_id, comment_id)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        
        comment.update(dto.getContent(), dto.isCommentIsAnonymous());
    } // 작성자 본인만 수정 가능
    
    @Transactional
    public void deleteCommentById(int post_id, int comment_id) {
        Comment comment = commentRepository.findByPostIdAndId(post_id, comment_id)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        
        commentRepository.delete(comment);
    } // 작성자 본인만 삭제 가능
}


