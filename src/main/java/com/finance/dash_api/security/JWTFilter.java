package com.finance.dash_api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.dash_api.POJO.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JWTFilter extends GenericFilter {

    private final JWTUtility jwtUtility;

    public JWTFilter(JWTUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String header = req.getHeader("Authorization");


        try {
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);
                System.out.println(token);
                String username = jwtUtility.extractUsername(token);
                String role = jwtUtility.extractRole(token);

                List<GrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority(role)
                );

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (ExpiredJwtException e) {
            sendError(res, "Token expired", HttpStatus.UNAUTHORIZED);
            return;
        } catch (SignatureException | MalformedJwtException e) {
            sendError(res, "Invalid token", HttpStatus.FORBIDDEN);
            return;
        } catch (Exception e) {
            sendError(res, "Authentication failed", HttpStatus.UNAUTHORIZED);
            return;
        }

        chain.doFilter(request, response);
    }

    private void sendError(HttpServletResponse response, String message, HttpStatus status)
            throws IOException {

        SecurityContextHolder.clearContext();

        ApiResponse<?> apiResponse = new ApiResponse<>(
                "Failed",
                message,
                null
        );

        response.setStatus(status.value());
        response.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(apiResponse));
    }
}
