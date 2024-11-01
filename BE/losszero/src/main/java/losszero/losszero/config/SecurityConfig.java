package losszero.losszero.config;

import jakarta.servlet.http.HttpServletRequest;
import losszero.losszero.jwt.CustomLogoutFilter;
import losszero.losszero.jwt.JWTFilter;
import losszero.losszero.jwt.JWTUtil;
import losszero.losszero.jwt.LoginFilter;
import losszero.losszero.repository.user.RefreshRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,JWTUtil jwtUtil,RefreshRepository refreshRepository) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors((corsCustomizer -> corsCustomizer
                    .configurationSource(new CorsConfigurationSource() {
                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                            CorsConfiguration configuration = new CorsConfiguration();
                            // 10/31 수정
                            configuration.setAllowedOrigins(Arrays.asList("http://k11e202.p.ssafy.io:5173", "http://localhost:5173","http://localhost:5500, https://k11e202.p.ssafy.io")); // https 주소 추가
                            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                            configuration.setAllowCredentials(true);
                            configuration.setAllowedHeaders(Collections.singletonList("*"));
                            configuration.setMaxAge(3600L);
                            configuration.setExposedHeaders(Collections.singletonList("Authorization"));
                            return configuration;
                        }
                    })));
        http
                .csrf((auth) -> auth.disable());
        http
                .formLogin((auth) -> auth.disable());
        http
                .httpBasic((auth) -> auth.disable());

        http
                .authorizeRequests((auth) -> auth
                        .requestMatchers("/login","/join","/api/v1/**").permitAll()
                        .requestMatchers("/reissue").permitAll()
                        .anyRequest().authenticated()); // 그외 다른 부분은 로그인한 자만 접근가능

        // 수정된 부분 시작: LoginFilter 인스턴스를 생성하고 setFilterProcessesUrl을 호출하여 경로 설정
        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository);
        loginFilter.setFilterProcessesUrl("/api/v1/login");  // 로그인 경로 설정
        // 수정된 부분 끝

        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        // 수정된 부분: LoginFilter를 UsernamePasswordAuthenticationFilter 위치에 추가

        // 수정된 부분: LoginFilter를 UsernamePasswordAuthenticationFilter 위치에 추가
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
//        http
//                .addFilterAt(
//                    new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil,refreshRepository).setFilterProcessUrl("/api/v1/login"), UsernamePasswordAuthenticationFilter.class);
        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil,refreshRepository), LogoutFilter.class);
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://k11e202.p.ssafy.io:5173", "http://localhost:5173", "http://localhost:5500"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "observe"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "USERID", "ROLE", "responseType", "observe"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}