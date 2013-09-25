package vwg.skoda.prcek.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.prcek.entities.UserZentaAdm;

@Service
@Transactional
public class UserZentaAdmService {
	
	static Logger log = Logger.getLogger(UserZentaAdm.class);
	
	@PersistenceContext(name = "UserService")
	private EntityManager entityManager;
	
	public UserZentaAdm getUserZentaAdm(String id) {
		log.debug("###\t\t getUserZentaAdm("+id+");");
		return entityManager.find(UserZentaAdm.class, id);
	}

}
