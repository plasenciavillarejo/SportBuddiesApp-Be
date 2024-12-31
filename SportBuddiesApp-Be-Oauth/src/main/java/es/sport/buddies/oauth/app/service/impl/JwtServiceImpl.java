package es.sport.buddies.oauth.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import es.sport.buddies.oauth.app.constantes.ConstantesApp;
import io.jsonwebtoken.Jwts;

@Service
public class JwtServiceImpl {
  
  /**
   * Función encargada de generar el token cuando se trabaja con el login de passkes, mantiene el mismo formato que el token generado por oauth 2.1
   * Para que el tokeng generado sea correcto se debe de agregar tanto la llave y el UUID generado en la clase SecurityConfig.java (jwkSource()) de lo contrario
   * siempre nos dará un 401.
   * @param subject
   * @param audience
   * @param roles
   * @param scopes
   * @param userId
   * @return
   */
  public String generateToken(String subject, String audience, List<String> roles, List<String> scopes, Long userId) {
    long now = System.currentTimeMillis();
    // 8 horas
    long expirationTime = now + (3600 * 8000);
    
    return Jwts.builder()
        .header().add("kid",ConstantesApp.uuidOauth)
        .add("alg", "RS256")
        .and()
        .subject(subject)
        .claim("aud", audience)
        .notBefore(new Date(now))
        .claim("scope", scopes)
        .claim("roles", roles)
        .issuer(ConstantesApp.ISSUERVALIDATETOKEN)
        .expiration(new Date(expirationTime))
        .issuedAt(new Date(now))
        .id(UUID.randomUUID().toString())
        .signWith(ConstantesApp.keyOauth.getPrivate())
        .claim("idusuario", userId)
        .compact();
  }
  
  public String generateRefreshToken(String subject, String audience, List<String> roles, List<String> scopes, Long userId) {
    long now = System.currentTimeMillis();
    // 12 horas
    long expirationTime = now + (3600 * 12000);
    
    return Jwts.builder()
        .header().add("kid",ConstantesApp.uuidOauth)
        .add("alg", "RS256")
        .and()
        .subject(subject)
        .claim("aud", audience)
        .notBefore(new Date(now))
        .claim("scope", scopes)
        .claim("roles", roles)
        .issuer(ConstantesApp.ISSUERVALIDATETOKEN)
        .expiration(new Date(expirationTime))
        .issuedAt(new Date(now))
        .id(UUID.randomUUID().toString())
        .signWith(ConstantesApp.keyOauth.getPrivate())
        .claim("idusuario", userId)
        .compact();
  }
  
}
