/*package es.sport.buddies.gateway.app.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.stereotype.Component;

import es.sport.buddies.entity.app.models.service.IClientesOauthService;
import es.sport.buddies.gateway.app.constantes.ConstantesGateway;
import reactor.core.publisher.Mono;

@Component
public class CustomReactiveClientRegistrationRepository implements ReactiveClientRegistrationRepository {

  private IClientesOauthService clientOauthService;

  public CustomReactiveClientRegistrationRepository(IClientesOauthService clientOauthService) {
    this.clientOauthService = clientOauthService;
  }

  @Override
  public Mono<ClientRegistration> findByRegistrationId(String registrationId) {
    return Mono.justOrEmpty(ConstantesGateway.CLIENTREGISTRATIONS.get(registrationId));
  }

}
*/