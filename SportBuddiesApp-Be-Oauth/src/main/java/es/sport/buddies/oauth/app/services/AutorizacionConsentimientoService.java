package es.sport.buddies.oauth.app.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import es.sport.buddies.entity.app.models.entity.AutorizacionConsentimento;
import es.sport.buddies.entity.app.models.service.IAutorizacionConsentimentoService;

@Component
public class AutorizacionConsentimientoService implements OAuth2AuthorizationConsentService {

  private IAutorizacionConsentimentoService autorizacionConsentimientoService;
  
  private final RegisteredClientRepository registeredClientRepository;
  
  public AutorizacionConsentimientoService(IAutorizacionConsentimentoService autorizacionService, RegisteredClientRepository registeredClientRepository) {
    Assert.notNull(autorizacionService, "autorizacionService cannot be null");
    Assert.notNull(registeredClientRepository, "registeredClientRepository cannot be null");
    this.autorizacionConsentimientoService = autorizacionService;
    this.registeredClientRepository = registeredClientRepository;
  }
  
  @Override
  public void save(OAuth2AuthorizationConsent authorizationConsent) {
    Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
    this.autorizacionConsentimientoService.guardarConsentimiento(toEntity(authorizationConsent));
  }

  @Override
  public void remove(OAuth2AuthorizationConsent authorizationConsent) {
    Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
    this.autorizacionConsentimientoService.deleteByIdClienteRegistradoAndPrincipalName(
        authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
  }

  @Override
  public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
    Assert.hasText(registeredClientId, "registeredClientId cannot be empty");
    Assert.hasText(principalName, "principalName cannot be empty");
    return this.autorizacionConsentimientoService.findByIdClienteRegistradoAndPrincipalName(
        registeredClientId, principalName).map(this::toObject).orElse(null);
  }

  private OAuth2AuthorizationConsent toObject(AutorizacionConsentimento authorizationConsent) {
    String registeredClientId = authorizationConsent.getIdClienteRegistrado();
    RegisteredClient registeredClient = this.registeredClientRepository.findById(registeredClientId);
    if (registeredClient == null) {
      throw new DataRetrievalFailureException(
          "The RegisteredClient with id '" + registeredClientId + "' was not found in the RegisteredClientRepository.");
    }

    OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent.withId(
        registeredClientId, authorizationConsent.getPrincipalName());
    if (authorizationConsent.getAuthorities() != null) {
      for (String authority : StringUtils.commaDelimitedListToSet(authorizationConsent.getAuthorities())) {
        builder.authority(new SimpleGrantedAuthority(authority));
      }
    }

    return builder.build();
  }

  private AutorizacionConsentimento toEntity(OAuth2AuthorizationConsent authorizationConsent) {
    AutorizacionConsentimento entity = new AutorizacionConsentimento();
    entity.setIdClienteRegistrado(authorizationConsent.getRegisteredClientId());
    entity.setPrincipalName(authorizationConsent.getPrincipalName());

    Set<String> authorities = authorizationConsent.getAuthorities()
        .stream()
        .map(auth -> auth.getAuthority()).collect(Collectors.toSet());
    
    entity.setAuthorities(StringUtils.collectionToCommaDelimitedString(authorities));

    return entity;
  }

}
