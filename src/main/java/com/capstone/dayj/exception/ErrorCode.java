package com.capstone.dayj.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 BAD_REQUEST: 잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    // 400 DATE_RANGE_ERROR: 잘못된 날짜 범위
    DATE_RANGE_ERROR(HttpStatus.BAD_REQUEST, "시작 날짜는 종료 날짜보다 이전이어야 합니다."),

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

    // 404 KEYWORD_NOT_FOUND : 키워드가 없음
    KEYWORD_NOT_FOUND(HttpStatus.NOT_FOUND, "생성된 키워드가 없습니다."),
    
    // 404 POST_NOT_FOUND : 게시물을 찾을 수 없음
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."),
    
    // 409 DUPLICATE_NICKNAME : 중복된 닉네임
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임입니다."),

    // 409 DUPLICATE_NICKNAME : 중복된 회원
    DUPLICATE_GROUP_MEMBER(HttpStatus.CONFLICT, "이미 존재하는 회원입니다."),

    // 402 IMAGE_NOT_FOUND: 어떤 이미지도 전송 받지 못함
    IMAGE_UPLOAD_FAIL(HttpStatus.PAYMENT_REQUIRED, "어떤 이미지도 전송되지 않았습니다."),

    // 413 IMAGE_NOT_FOUND: 어떤 이미지도 전송 받지 못함
    TOO_MANY_IMAGE(HttpStatus.PAYLOAD_TOO_LARGE, "이미지를 1개만 전송해주세요.");
    
    private final HttpStatus status;
    private final String message;
}