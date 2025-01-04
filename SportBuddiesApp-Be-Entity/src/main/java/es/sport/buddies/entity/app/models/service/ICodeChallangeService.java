package es.sport.buddies.entity.app.models.service;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.CodeChallange;

public interface ICodeChallangeService {

  public void guardarCodeChallange(CodeChallange codeChallange);
  
  public CodeChallange findByCodeChallange(@Param("codeChallange") String codeChallange);
 
  public void eliminarCodeChallangeValidado(long codeChallangeId);
  
}
