package s5almiakki.oauth2practice.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import s5almiakki.oauth2practice.dto.CustomOAuth2User;
import s5almiakki.oauth2practice.dto.NaverOAuth2Response;
import s5almiakki.oauth2practice.dto.OAuth2Response;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

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
        String role = "ROLE_USER";
        return new CustomOAuth2User(response, role);
    }
}
