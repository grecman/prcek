package vwg.skoda.prcek.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.prcek.entities.UserZentaAdm;

@Service
public class UserZentaAdmService {
	
	static Logger log = Logger.getLogger(UserZentaAdm.class);
	
	@PersistenceContext(name = "UserService")
	private EntityManager entityManager;
	
	public UserZentaAdm getUserZentaAdm(String id) {
		log.trace("###\t\t getUserZentaAdm("+id+");");
		return entityManager.find(UserZentaAdm.class, id);
	}

}
