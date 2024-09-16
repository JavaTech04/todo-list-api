package com.javatech.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatech.dto.response.ExceptionResponse;
import com.javatech.service.JwtService;
import com.javatech.service.TokenService;
import com.javatech.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.Date;

import static com.javatech.utils.TokenType.ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class PreFilter extends OncePerRequestFilter {

    private final UserService userService;

    private final JwtService jwtService;

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("========== doFilterInternal ==========");
        final String authorization = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String token = authorization.substring("Bearer ".length());
        try {
            final String username = this.jwtService.extractUsername(token, ACCESS_TOKEN);
            if(this.tokenService.getToken(username) == null) {
                throw new Exception("Authentication failed");
            }
            if (StringUtils.isNotEmpty(username) & SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userService.userDetailsService().loadUserByUsername(username);
                if (this.jwtService.isValid(token, ACCESS_TOKEN, userDetails)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authenticationToken);
                    SecurityContextHolder.setContext(context);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.error("Token expired");
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            exceptionResponse.setTimestamp(new Date());
            exceptionResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            exceptionResponse.setPath(request.getRequestURI());
            exceptionResponse.setError("Token expired");
            exceptionResponse.setMessage(e.getMessage());

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
        } catch (Exception e) {
            log.error("Authentication error");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication failed");
        }
    }
}
