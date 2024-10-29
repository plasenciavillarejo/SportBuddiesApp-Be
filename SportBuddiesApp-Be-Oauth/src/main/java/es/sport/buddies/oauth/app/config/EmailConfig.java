package es.sport.buddies.oauth.app.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

  @Value("${mail.host}")
  private String mailHost;
  
  @Value("${mail.port}")
  private String mailPort;
  
  @Value("${mail.username}")
  private String mailUserName;

  @Value("${mail.password}")
  private String mailPassword;
  
  @Value("${mail.properties.mail.smtp.auth}")
  private boolean mailAuth;
                      
  @Value("${mail.properties.mail.smtp.starttls.enable}")
  private boolean mailStartTtlsEnabled;
  
  @Value("${mail.properties.mail.smtp.starttls.required}")
  private boolean mailRequired;
  
  @Value("${mail.properties.mail.smtp.connectiontimeout}")
  private int mailConnectiontimeOut;
                                  
  @Value("${mail.properties.mail.smtp.timeout}")
  private int mailSmtpTimeOut;
  
  @Value("${mail.properties.mail.smtp.writetimeout}")
  private  int mailSmtpWriteTimeOut;

  @Value("${mail.host.mail.dog}")
  private String mailHostDog;
  
  @Value("${mail.port.mail.dog}")
  private String mailPortDog;
  
  @Bean
  JavaMailSenderImpl javaMailSenderImpl() {
    
    JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();

    mailSenderImpl.createMimeMessage();
    mailSenderImpl.setHost(!mailHost.isEmpty() ? mailHost : mailHostDog);
    mailSenderImpl.setPort(!mailPort.isEmpty() ? Integer.valueOf(mailPort) : Integer.valueOf(mailPortDog));
    
    mailSenderImpl.setUsername(mailUserName);
    mailSenderImpl.setPassword(mailPassword);
    
    Properties props = mailSenderImpl.getJavaMailProperties();
    props.put("mail.smtp.auth", mailAuth);
    props.put("mail.smtp.starttls.enable", mailStartTtlsEnabled);
    props.put("smtp.starttls.required", mailRequired);
    props.put("mail.smtp.connectiontimeout", mailConnectiontimeOut);
    props.put("mail.smtp.timeout", mailSmtpTimeOut);
    props.put("mail.smtp.writetimeout", mailSmtpWriteTimeOut);
    
    /* Seguridad STARTLS activada
    props.put(Constantes.MAIL_SERVER_AUTH, 
        parametros.get(Constantes.AUTHENTICATION_MODE).equalsIgnoreCase(Constantes.C_YES) ? 
            Constantes.MAIL_CONS_TRUE : Constantes.MAIL_CONS_FALSE);    
    props.put(Constantes.MAIL_MIME_CHARSET, Constantes.MAIL_MIME_CHARSET_UTF8); */
    
    return mailSenderImpl;
  }
  
}
