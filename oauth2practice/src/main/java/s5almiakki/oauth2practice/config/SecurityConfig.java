package s5almiakki.oauth2practice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import s5almiakki.oauth2practice.service.CustomOAuth2UserService;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
//    private final CustomClientRegistrationRepository customClientRegistrationRepository;
    private final ClientRegistrationRepository  clientRegistrationRepository;
    private final OAuth2AuthorizedClientService  oAuth2AuthorizedClientService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                // oauth2Login은 자동으로 여러가지 설정해주는 반면, oauth2Client는 커스텀할 것들이 많다.
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .clientRegistrationRepository(clientRegistrationRepository)
                        .authorizedClientService(oAuth2AuthorizedClientService)
                        .userInfoEndpoint(config -> config
                                .userService(customOAuth2UserService)))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/oauth2/**", "/login/**").permitAll()
                        .anyRequest().authenticated())
                .build();
    }
}
