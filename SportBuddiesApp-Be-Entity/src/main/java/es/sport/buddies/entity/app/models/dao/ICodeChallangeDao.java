package es.sport.buddies.entity.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.CodeChallange;

public interface ICodeChallangeDao extends JpaRepository<CodeChallange, Long>{

  public CodeChallange findByCodeChallange(@Param("codeChallange") String codeChallange);
  
}
