package com.capstone.dayj.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 BAD_REQUEST: 잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    
    // 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),
    
    // 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),
    
    // 404 PLAN_NOT_FOUND : 계획을 찾을 수 없음
    PLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "계획을 찾을 수 없습니다."),
    
    // 404 FRIEND_GROUP_NOT_FOUND : 계획을 찾을 수 없음
    FRIEND_GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "그룹을 찾을 수 없습니다."),
    
    // 404 APP_USER_NOT_FOUND : 회원을 찾을 수 없음
    APP_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    
    // 404 COMMENT_NOT_FOUND : 댓글을 찾을 수 없음
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    
    // 404 REPLY_NOT_FOUND : 대댓글 찾을 수 없음
    REPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "대댓글을 찾을 수 없습니다."),
    
    // 404 POST_NOT_FOUND : 게시물을 찾을 수 없음
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."),

    // 409 DUPLICATE_NICKNAME : 중복된 닉네임
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임입니다.");

    private final HttpStatus status;
    private final String message;
}