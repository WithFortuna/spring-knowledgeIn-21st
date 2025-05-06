package com.ceos21.knowledgeIn.domain.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    ROLE_USER("사용자"), ROLE_ADMIN("관리자");

    private final String value;
}
