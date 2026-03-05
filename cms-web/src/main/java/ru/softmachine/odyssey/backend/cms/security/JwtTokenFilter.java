package com.aspnt.mddl.security;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = authHeader.substring(7);
        try {
            Map<String, Object> parsedClaims = Jwts.parser()
                    .unsecured()
                    .build()
                    .parseUnsecuredClaims(token)
                    .getPayload();

            List<String> actions = (List<String>) parsedClaims.getOrDefault("permissions", List.<String>of());
            log.info("Actions: {}", actions);
            log.info("Name: {}", parsedClaims.getOrDefault("name", null));

            var userInfo = new UserInfo(
                    parsedClaims.getOrDefault("sub", "unknown").toString(),
                    parsedClaims.getOrDefault("name", "unknown").toString(),
                    actions.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList()
            );

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userInfo,
                    null,
                    userInfo.actions()
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            log.warn("Error parsing JWT: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
