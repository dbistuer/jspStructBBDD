package cat.almata.daw.llistatUsuaris;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;


public class NouProducteAction extends DBAction implements SessionAware{
	
	private Producte producte = null;
	

	private Map<String, Object> aplication;
	private Map<String, Object> session;

	public Map<String, Object> getAplication() {
		return aplication;
	}

	public Producte getProducte() {
		return producte;
	}

	public void setProducte(Producte producte) {
		this.producte = producte;
	}
	
	public String crear(){
		// Si tenim dades d'usuari per omplir i hem recollit la llista d'usuaris correctament de session
		// caldrà afegir aquest usuari a la llista.
		// en cas que tinguem dades d'usuari, però aquest ja existeixi (el seu login) caldrà que informem 
		// d'aquest error amb la funció addActionError();
		Hashtable<String,Producte> llistaProductes = (Hashtable<String,Producte>)aplication.get(Constants.sessioLista);
		/*if(usuari.getPass().equals(usuari.getPassword())){
			addActionError("newUser.equalsPass");
			return INPUT;
		}*/
		if(producte!=null && llistaProductes!=null) {
			cargaDB();
			//llistaProductes = (Hashtable<String, Producte>)aplication.get(Constants.sessioLista);
			db.crearProducte(producte);
			return SUCCESS;
			
		}else {
			// Si no tenim dades d'usuari, fem un new i tornem al formulari
			producte = new Producte();
			return INPUT;
		}
		
	}
	
	public Date getMinDate() throws ParseException{
	    DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
	    return dateFormat.parse("25/01/1960");
	}
	
	public Date getMaxDate() throws ParseException{
	    DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
	    return dateFormat.parse("29/01/2001");
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
