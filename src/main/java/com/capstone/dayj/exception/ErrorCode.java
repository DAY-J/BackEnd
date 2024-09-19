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
    
    // 404 TOKEN_NOT_FOUND : DB에 해당 유저의 refresh 토큰 없음.
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저의 리프레시 토큰이 DB에 없습니다."),
    
    // 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),
    
    // 406 REFRESH_TOKEN_EXPIRED : 리프레시 토큰 만료됨.
    REFRESH_TOKEN_EXPIRED(HttpStatus.NOT_ACCEPTABLE, "리프레시 토큰이 만료되었습니다."),
    
    // 409 DUPLICATE_NICKNAME : 중복된 닉네임
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임입니다."),
    
    // 409 DUPLICATE_USERNAME : 중복된 유저네임(OAuth의 경우 email)
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "중복된 유저네임입니다."),
    
    // 409 DUPLICATE_NICKNAME : 중복된 회원
    DUPLICATE_GROUP_MEMBER(HttpStatus.CONFLICT, "이미 존재하는 회원입니다."),
    
    // 409 TOKEN_INCONSISTENCY : token category가 refresh가 아님.
    TOKEN_INCONSISTENCY(HttpStatus.CONFLICT, "리프레시 토큰이 아닙니다."),
    
    
    // 413 TOO_MANY_IMAGE: 이미지를 너무 많이 보냈음
    TOO_MANY_IMAGE(HttpStatus.PAYLOAD_TOO_LARGE, "이미지를 1개만 전송해주세요."),
    
    // 428 IMAGE_UPLOAD_FAIL: 이미지가 전송되지 않음
    IMAGE_UPLOAD_FAIL(HttpStatus.PRECONDITION_REQUIRED, "어떤 이미지도 전송되지 않았습니다."),
    
    // 428 REFRESH_TOKEN_EMPTY : header에 refresh 토큰 없음
    REFRESH_TOKEN_EMPTY(HttpStatus.PRECONDITION_REQUIRED, "리프레시 토큰이 비어있습니다."),
    
    // 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다.");
    
    private final HttpStatus status;
    private final String message;
}