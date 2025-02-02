package es.sport.buddies.oauth.app.service.impl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import es.sport.buddies.oauth.app.constantes.ConstantesApp;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

  @Autowired
  private JavaMailSender emailSender;

  @Value("${mail.username}")
  private String mailUserName;
  
  /**
   * Función encargada de envíar el correo electrónico al usuario con el código de doble factor
   * @param correoDestinatario
   * @param codigoVerificacion
   * @param nombreUsuario
   */
  public void sendEmailCodeVerification(String correoDestinatario, String codigoVerificacion, String nombreUsuario) {
    try {
      String htmlContent = new String(Files.readAllBytes(Paths.get(ConstantesApp.PLANTILLA)), StandardCharsets.UTF_8);
      htmlContent = htmlContent.replace(ConstantesApp.CODE, codigoVerificacion).replace(ConstantesApp.USERNAME, nombreUsuario);
      
      MimeMessage mimeMessage = emailSender.createMimeMessage();
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, ConstantesApp.UTF8);
      
      messageHelper.setFrom(mailUserName);
      messageHelper.setTo(correoDestinatario);
      messageHelper.setSubject(ConstantesApp.CODIFOVERIFICACION);
      messageHelper.setText(htmlContent, true);

      emailSender.send(mimeMessage);
    } catch (Exception e) {
      LOGGER.error("Error al enviar el mensaje: {}", e.getMessage(), e.getCause());
    }
  }

}
