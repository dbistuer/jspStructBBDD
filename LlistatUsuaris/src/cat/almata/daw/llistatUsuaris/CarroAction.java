package cat.almata.daw.llistatUsuaris;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class CarroAction extends DBAction implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Producte> productes;
	private Map<Integer, Boolean> checkboxes;
	private Producte producte;
	private Usuari usuari = null;

	private Map<String, Object> session;

	
	public String llistarCarro() {
		// Aquí hem d'agafar de session la llista d'usuaris
		// En cas que no existeixi el crearem i el posarem 
		// en l'àmbit session.
		// si existeix ho passarem a una Collection
		setCheckboxes(checkboxes);
		cargaDB();
		
		usuari = (Usuari)session.get(Constants.sessioUsuari);
		usuari = db.getUsuari(usuari.getLogin());
		if(usuari != null) {
			ArrayList<Producte> llistatProductes = (ArrayList<Producte>) db.obtenirProductesCarro(usuari.getId());
		if(llistatProductes == null) {
			llistatProductes = new ArrayList<Producte>();
		}
		//application.put(Constants.llistatProductes, llistatProductes);
		productes = llistatProductes;
		return SUCCESS;
		}else {
			return "login";
		}
		
	}

	public String comprar() {
		cargaDB();
		Usuari u = (Usuari) session.get(Constants.sessioUsuari);
    	u = db.getUsuari(u.getLogin());
	    productes = (List<Producte>) db.obtenirProductesCarro(u.getId());
	    Iterator<Producte> iterador = productes.iterator();
	    while(iterador.hasNext()) {
	    	int e=db.compraProducte(iterador.next(),u.getId());
	    }
		return SUCCESS;
	}
	
	public String eliminar() {
		cargaDB();
		setCheckboxes(checkboxes);
		if(checkboxes!=null){
			Iterator<Map.Entry<Integer, Boolean>> entries = checkboxes.entrySet().iterator();
			while (entries.hasNext()) {
			    Map.Entry<Integer, Boolean> entry = entries.next();
			    if(entry.getValue()) {
				    producte = db.obtenirProducte(entry.getKey().intValue());
			    	Usuari u = (Usuari) session.get(Constants.sessioUsuari);
			    	u = db.getUsuari(u.getLogin());
			    	int e=db.eliminaProducte(producte,u.getId());
				    }
			    }
			return SUCCESS;
			}else{
				return "CheckNoLoad";
			}
	}
	
	public void cargaDB() {
		if(db==null) {
			this.loadDB();
		}
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session=(SessionMap<String, Object>) session;
	}
	
	public Producte getProducte() {
		return producte;
	}

	public void setProducte(Producte producte) {
		this.producte = producte;
	}
	
	public Collection<Producte> getProductes() {
		return productes;
	}

	public void setProductes(Collection<Producte> productes) {
		this.productes = (List<Producte>) productes;
	}
	
	public void setCheckboxes(Map<Integer, Boolean> checkboxes) {
		this.checkboxes = checkboxes;
	}
	
	public Map<Integer, Boolean> getCheckboxes() {
		return checkboxes;
	}
	
}
