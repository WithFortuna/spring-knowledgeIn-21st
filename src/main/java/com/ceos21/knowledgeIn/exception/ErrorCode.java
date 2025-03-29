package com.ceos21.knowledgeIn.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    THIS_IS_EXAMPLE(HttpStatus.BAD_REQUEST, "예시용 에러 코드입니다");

    private final HttpStatus httpStatus;
    private final String message;

}
