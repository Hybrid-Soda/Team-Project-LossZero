package losszero.losszero.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import losszero.losszero.dto.user.CustomUserDetailDTO;
import losszero.losszero.entity.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

import java.io.IOException;
import java.util.Collections;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        // 포트 5500에서의 요청에 대해 인증을 생략하고 익명 사용자를 설정
        if ("127.0.0.1".equals(request.getRemoteHost()) && request.getRemotePort() == 5500) {
            Authentication anonymousAuth = new AnonymousAuthenticationToken("key", "anonymousUser",
                    AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
            SecurityContextHolder.getContext().setAuthentication(anonymousAuth);
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authorization);
        System.out.println("authorization now");
        System.out.println("All Headers: " + Collections.list(request.getHeaderNames()));
        System.out.println("Authorization Header: " + authorization);
        System.out.println("Request URL: " + request.getRequestURI());
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("Authorization Header: " + authorization);

        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            return;
        }

        String token = authorization.split(" ")[1];
        System.out.println("Extracted Token: " + token);
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            filterChain.doFilter(request, response);
            return;
        }
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);
        User user = new User();
        user.setUsername(username);
        user.setPassword("temppassword");
        user.setRole(role);

        CustomUserDetailDTO customUserDetailDTO = new CustomUserDetailDTO(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetailDTO, null, customUserDetailDTO.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);


    }
}
