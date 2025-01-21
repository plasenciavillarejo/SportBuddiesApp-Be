package es.sport.buddies.gateway.app.utilidades;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Utilidades {

  private Utilidades() {
    // Constructor vacio
  }
  
  /**
   * Función encargada de mapear todas las rutas públicas dentro de la aplicacion
   */
  public List<String> publicRoutes = Arrays.asList("/authorized", 
      "/logout", "/oauth2/**",
      "/api/main/reservaActividad/listarReserva", 
      "/api/main/reservaActividad/comboInicio",
      "/api/main/reservaActividad/listadoMunicipios", 
      "/api/main/reservaActividad/listadoReserva",
      "/api/main/borrarCookie", 
      "/api/main/paypal/estado/pago", 
      "/api/main/usuario/crear",
      "/api/oauth2/**");
  
}
