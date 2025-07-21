package s5almiakki.oauth2practice.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@RequiredArgsConstructor
@Configuration
public class CustomClientRegistrationRepository {

    private final CustomClientRegistration customClientRegistration;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                customClientRegistration.naverClientRegistration());
    }
}
