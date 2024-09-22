package es.sport.buddies.main.app.config;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtDecoderConfig {

  @Bean
  JwtDecoder jwtDecoder() {
    RSAPublicKey publicKey = importPublicKey();
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
  }

  private static RSAPublicKey importPublicKey() {
    try (FileInputStream fis = new FileInputStream("/Users/joseplasenciavillarejo/Desktop/sportbuddies_repository/llave.pem")) {
      byte[] encodedPublicKey = fis.readAllBytes();
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedPublicKey);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}
