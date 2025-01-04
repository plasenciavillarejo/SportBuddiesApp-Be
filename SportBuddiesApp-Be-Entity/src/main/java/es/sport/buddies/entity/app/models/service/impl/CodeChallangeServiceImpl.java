package es.sport.buddies.entity.app.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.ICodeChallangeDao;
import es.sport.buddies.entity.app.models.entity.CodeChallange;
import es.sport.buddies.entity.app.models.service.ICodeChallangeService;

@Service
public class CodeChallangeServiceImpl implements ICodeChallangeService {

  @Autowired
  private ICodeChallangeDao codeChallangeDao;
  
  @Override
  @Transactional
  public void guardarCodeChallange(CodeChallange codeChallange) {
    codeChallangeDao.save(codeChallange);
  }

  @Override
  @Transactional(readOnly = true)
  public CodeChallange findByCodeChallange(String codeChallange) {
    return codeChallangeDao.findByCodeChallange(codeChallange);
  }

  @Override
  @Transactional
  public void eliminarCodeChallangeValidado(long codeChallangeId) {
    codeChallangeDao.deleteById(codeChallangeId);
  }

}
