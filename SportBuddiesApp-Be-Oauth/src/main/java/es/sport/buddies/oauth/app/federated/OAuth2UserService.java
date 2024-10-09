package es.sport.buddies.oauth.app.federated;

import java.nio.file.attribute.UserPrincipal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.dto.UsernameAuthenticationDto;
import es.sport.buddies.entity.app.models.entity.UsuarioGoogle;
import es.sport.buddies.entity.app.models.service.IUsuarioGoogleService;

@Service
public class OAuth2UserService implements org.springframework.security.oauth2.client.userinfo.OAuth2UserService<OAuth2UserRequest, OAuth2User>  {

  @Autowired
  private IUsuarioGoogleService googleService;
  
  
  
  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
      OAuth2User oAuth2User = new DefaultOAuth2User(null, null, null); 
      //super.loadUser(oAuth2UserRequest);
      return processOAuth2User(oAuth2UserRequest, oAuth2User);
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
      /*Oauth2UserInfoDto userInfoDto = Oauth2UserInfoDto
              .builder()
              .name(oAuth2User.getAttributes().get("name").toString())
              .id(oAuth2User.getAttributes().get("sub").toString())
              .email(oAuth2User.getAttributes().get("email").toString())
              .picture(oAuth2User.getAttributes().get("picture").toString())
              .build();*/

      Optional<UsuarioGoogle> userOptional = googleService.findByEmail(null);
      /*User user = userOptional
              .map(existingUser -> updateExistingUser(existingUser, userInfoDto))
              .orElseGet(() -> registerNewUser(oAuth2UserRequest, userInfoDto));*/
      // UserPrincipal.create(u, oAuth2User.getAttributes());
      return null;
  }

  /*
  private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, Oauth2UserInfoDto userInfoDto) {
      User user = new User();
      user.setProvider(oAuth2UserRequest.getClientRegistration().getRegistrationId());
      user.setProviderId(userInfoDto.getId());
      user.setName(userInfoDto.getName());
      user.setUsername(userInfoDto.getEmail());
      user.setPicture(userInfoDto.getPicture());
      user.setId(UUID.randomUUID());
      return userRepository.save(user);
  }

  private User updateExistingUser(User existingUser, Oauth2UserInfoDto userInfoDto) {
      existingUser.setName(userInfoDto.getName());
      existingUser.setPicture(userInfoDto.getPicture());
      return userRepository.save(existingUser);
  }
*/

}
