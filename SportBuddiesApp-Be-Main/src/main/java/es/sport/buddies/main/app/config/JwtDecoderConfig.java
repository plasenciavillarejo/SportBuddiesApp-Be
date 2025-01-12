package es.sport.buddies.main.app.config;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import es.sport.buddies.main.app.constantes.ConstantesMain;

/*@Configuration
 Forzamos que cargue antes el @PostConstruct, ya que al tener el filtro JwtAuthenticationFilter con un constructor, por defecto va a cargar 
 * antes que el @PostConstruct y el valor de ConstantesMain.FICHERPEMPLUBLIKEY no contiene el valor asigando de forma externa

@DependsOn("inicializacion")
public class JwtDecoderConfig {

  @Bean
  JwtDecoder jwtDecoder() {
    RSAPublicKey publicKey = importPublicKey();
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
  }

  private static RSAPublicKey importPublicKey() {
    try (FileInputStream fis = new FileInputStream(ConstantesMain.FICHERPEMPLUBLIKEY)) {
      byte[] encodedPublicKey = fis.readAllBytes();
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedPublicKey);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}*/
