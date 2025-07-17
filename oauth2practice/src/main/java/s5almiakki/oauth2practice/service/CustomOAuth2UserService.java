package s5almiakki.oauth2practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import s5almiakki.oauth2practice.dto.CustomOAuth2User;
import s5almiakki.oauth2practice.dto.NaverOAuth2Response;
import s5almiakki.oauth2practice.dto.OAuth2Response;
import s5almiakki.oauth2practice.entity.UserEntity;
import s5almiakki.oauth2practice.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response response;
        switch (registrationId) {
            case "naver":
                response = new NaverOAuth2Response(oAuth2User.getAttributes());
                break;
            default:
                return null;
        }
        String username = response.getProvider() + "_" + response.getProviderId();
        String role = null;
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            role = user.get().getRole();
        } else {
            userRepository.save(new UserEntity(username, role));
        }
        return new CustomOAuth2User(response, role);
    }
}
