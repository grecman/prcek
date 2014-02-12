package vwg.skoda.prcek.objects;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;


import org.apache.log4j.Logger;

public class RoleFiltr implements Filter {
	
	static Logger log = Logger.getLogger(RoleFiltr.class);
	static final String USER_ATTRIBUTE = RoleFiltr.class.getCanonicalName() + ".USER_ATTRIBUTE";
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//log.debug("doFilter(" + request + ", " + response + ", " + chain + ")");
		final HttpServletRequest req = (HttpServletRequest)request;
		
		req.setCharacterEncoding("UTF-8");
		
		HttpSession s = req.getSession(true);
		if (s.getAttribute(USER_ATTRIBUTE) == null) {
			log.trace("Logging user info... (sessionId:"+s.getId()+")");
			
			// nevyhoda tohoto reseni je, ze tahame EntityManager do teto vrstvy a je treba resit veci okolo primo zde (treba transakce, ktere jinak resime vsechny v servisach )
/*			EntityManager em = Persistence.createEntityManagerFactory("CocafopService").createEntityManager();
			Protokol p = new Protokol(); 
			p.setNetUserName(req.getUserPrincipal().getName().toUpperCase());
			p.setTime(new Date());
			p.setAction("Start aplikace");
			p.setInfo("poprve v sessione");
			p.setSessionId(s.getId());
			
			EntityTransaction t  = em.getTransaction();
			t.begin();
			em.persist(p);
			t.commit();
*/			
			s.setAttribute(USER_ATTRIBUTE, req.getUserPrincipal().getName().toUpperCase());
		}
		
		/*
		 *  
		 * Pokud na JSP zavolam cokoliv v hranatych [] zavorkach pr.: ${role['ADMINS']} 		
		 * tak se (V TOMTO PRIPADE) vola nize uvedena metoda getRole s navratovou hodnotou MAP<String, Boolean> s tim, ze
		 * do KEY da string hodnotu z tech zavorek a pro Boolean si zavola get a vrati isUserInRole((String) key) 
		 * 
		 *  No celkem prasarna :(
		 */	
		Map<String, Boolean> map = new HashMap<String, Boolean>() {
			static final long serialVersionUID = 1L;

			@Override
			public Boolean get(Object key) {
				log.trace("### get(" + key + ")     (doFilter - role)");
				Boolean t = req.isUserInRole((String) key);
				log.trace("\t\t" + t);
				return t;
			}
		};

		req.setAttribute("role", map);
		
		chain.doFilter(req, response);
	}


	public static class PrcekRequestWrapper extends HttpServletRequestWrapper {

		public PrcekRequestWrapper(HttpServletRequest req) {
			super(req);
		}
		
	}


	public void destroy() {
		

	}


	public void init(FilterConfig arg0) throws ServletException {
		
	}
	

}
