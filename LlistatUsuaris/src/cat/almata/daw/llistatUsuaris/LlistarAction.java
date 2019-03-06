package cat.almata.daw.llistatUsuaris;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class LlistarAction extends DBAction implements ApplicationAware,SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<Producte> productes;
	private Producte producte;
	private Usuari usuari = null;
	private String actiu = "Actiu";

	
	private Map<String, Object> application;
	private Map<String, Object> session;

	public Map<String, Object> getApplication() {
		return application;
	}
	
	
	public Producte getUsuari() {
		return producte;
	}

	public void setProducte(Producte producte) {
		this.producte = producte;
	}
	
	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}
	
	public Collection<Producte> getProducte() {
		return productes;
	}

	public void setProducte(Collection<Producte> productes) {
		this.productes = productes;
	}
	
	public String llistarProductes() {
		// Aquí hem d'agafar de session la llista d'usuaris
		// En cas que no existeixi el crearem i el posarem 
		// en l'àmbit session.
		// si existeix ho passarem a una Collection
		
		cargaDB();
		
		Hashtable<Integer,Producte> llistatProductes = db.obtenirProductes();
		usuari = (Usuari)session.get(Constants.sessioUsuari);
		if(usuari != null) {
		if(llistatProductes == null) {
			llistatProductes = new Hashtable<Integer,Producte>();
		}
		application.put(Constants.llistatProductes, llistatProductes);
		productes = llistatProductes.values();
		return SUCCESS;
		}else {
			return "no user";
		}
		
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session=(SessionMap<String, Object>) session;
	}
	
	public void cargaDB() {
		if(db==null) {
			this.loadDB();
		}
	}


	
	

}
