package com.ceos21.knowledgeIn.security.handler;

import com.ceos21.knowledgeIn.security.auth.jwt.JwtTokenProvider;
import com.ceos21.knowledgeIn.security.auth.jwt.refresh.RefreshTokenService;
import com.ceos21.knowledgeIn.security.auth.user.detail.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenService refreshService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = tokenProvider.generateAccessToken(userDetails.getUserId(), userDetails.getUsername());
        String refreshToken = tokenProvider.generateRefreshToken(userDetails.getUserId(), userDetails.getUsername());

        refreshService.saveToken(userDetails.getUserId(), refreshToken);

        res.addHeader("Access", accessToken);   // TODO: accesstoken과 refresh 토큰 전달 방식 개선필요
        res.addHeader("Refresh", refreshToken);
        res.setStatus(HttpStatus.CREATED.value());
    }
}
