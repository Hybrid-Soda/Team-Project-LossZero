package losszero.losszero.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())) // CORS 설정 추가
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/line/**").permitAll()  // 인증 없이 접근 가능
                        .requestMatchers("/api/v1/daily/**").permitAll()  // 인증 없이 접근 가능
                        .requestMatchers("/api/v1/weekly/**").permitAll()  // 인증 없이 접근 가능
                        .requestMatchers("/api/v1/operation/**").permitAll()  // 인증 없이 접근 가능
                        .requestMatchers("/api/v1/realtime/**").permitAll()  // 인증 없이 접근 가능
                        .requestMatchers("/api/v1/circumstance/**").permitAll()  // 인증 없이 접근 가능
                        .requestMatchers("/api/v1/auth/**").permitAll()  // 로그인/회원가입 경로 허용
                        .requestMatchers("/api/v1/product/**").permitAll()  // 실시간 데이터 조회 허용
                        .requestMatchers("/login", "/signup").permitAll()  // 로그인/회원가입 페이지 허용
                        .anyRequest().authenticated()  // 그 외 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/login")  // 사용자 정의 로그인 페이지
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());  // 로그아웃 설정

        return http.build();
    }

    // PasswordEncoder 빈 추가 (BCrypt 사용 권장)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 테스트용 인메모리 유저 설정 (UserDetailsService로 DB 연동 가능)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
