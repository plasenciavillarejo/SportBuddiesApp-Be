package es.sport.buddies.main.app.filter;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import es.sport.buddies.entity.app.dto.UsernameAuthenticationDto;
import es.sport.buddies.entity.app.dto.UsuarioDto;
import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.service.IUsuarioService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
    
    @Autowired
    private IUsuarioService usuarioService;
    
/*   
    @Autowired
    private final JwtDecoder jwtDecoder;

    public JwtAuthenticationFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }
     
*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
      String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
      
      if (authHeader != null && authHeader.startsWith(ConstantesMain.BEARER)) {
        if (request.getRequestURI().contains("/reservaUsuario/eliminar/")) {
          ConstantesMain.TOKEN = authHeader.substring(7);
        } else if(!request.getRequestURI().contains("/reservaUsuario/eliminar/") && !ConstantesMain.TOKEN.isEmpty()) {
          ConstantesMain.TOKEN = "";
        }
        try {
       
          SignedJWT signedJWT = SignedJWT.parse(authHeader.substring(7));
          JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
          Map<String, Object> claims = claimsSet.getClaims();
          
       
          
          String userName = claims.get(ConstantesMain.SUB).toString();
          List<String> rolString = claimsSet.getStringListClaim(ConstantesMain.ROLES);

          Usuario usuario = usuarioService.findByNombreUsuario(userName);
         
          if(usuario == null ) {
            usuario = usuarioService.findByEmail(userName);
          }
          
          List<GrantedAuthority> roles = rolString.stream()
              .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role)).toList();

          UsernameAuthenticationDto userAuthentication = new UsernameAuthenticationDto(usuario, null, roles);

          UsuarioDto usuDto = new UsuarioDto();
          usuDto.setIdUsuario(usuario.getIdUsuario());
          usuDto.setNombreUsuario(usuario.getNombreUsuario());
          userAuthentication.setUsuarioDto(usuDto);

          // Configura el SecurityContextHolder con la Authentication
          SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        } catch (JwtException | ParseException e) {
          LOGGER.error("Error: {}", e.getMessage());
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NO ESTÁS AUTORIZADO NO TIENES TOKEN");
          return;
        }
      }
      filterChain.doFilter(request, response);
    }
}
