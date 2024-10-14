package es.sport.buddies.oauth.app.denied.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.sport.buddies.entity.app.models.entity.AccesoDenegadoDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    AccesoDenegadoDto accesoDenegado = new AccesoDenegadoDto();
    accesoDenegado.setMessage("Acceso denegado");
    accesoDenegado.setStatus(HttpStatus.FORBIDDEN.value());

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(accesoDenegado);

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.getWriter().write(json);

  }

}
