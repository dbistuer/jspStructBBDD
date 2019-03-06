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


public class NouUsuariAction extends ActionSupport implements ApplicationAware,SessionAware{
	
	private Usuari usuari = null;
	private String pass;
	private String password;

	private Map<String, Object> aplication;
	private Map<String, Object> session;

	public Map<String, Object> getAplication() {
		return aplication;
	}

	@Override
	public void setApplication(Map<String, Object> aplication) {
		this.aplication = aplication;
	}

	public Usuari getUsuari() {
		return usuari;
	}

	public void setUsuari(Usuari usuari) {
		this.usuari = usuari;
	}
	
	public String crear(){
		// Si tenim dades d'usuari per omplir i hem recollit la llista d'usuaris correctament de session
		// caldrà afegir aquest usuari a la llista.
		// en cas que tinguem dades d'usuari, però aquest ja existeixi (el seu login) caldrà que informem 
		// d'aquest error amb la funció addActionError();
		Hashtable<String,Usuari> llistaUsuaris = (Hashtable<String,Usuari>)aplication.get(Constants.sessioLista);
		/*if(usuari.getPass().equals(usuari.getPassword())){
			addActionError("newUser.equalsPass");
			return INPUT;
		}*/
		if(usuari!=null && llistaUsuaris!=null) {
				llistaUsuaris = (Hashtable<String, Usuari>)aplication.get(Constants.sessioLista);
			if(!llistaUsuaris.containsKey(usuari.getPass())) {
				pass = usuari.getPass();
				password = usuari.getPassword();
				if(pass.equals(password)) {
					llistaUsuaris.put(usuari.getPass(),usuari);
					aplication.put(Constants.sessioLista, llistaUsuaris);
					return SUCCESS;
				}else {
					addActionError("newUser.equalsPass");
					return INPUT;
				}
			}else {
				addActionError("newUser.errorUserExists");
				return INPUT;
			}

			
		}else {
			// Si no tenim dades d'usuari, fem un new i tornem al formulari
			usuari = new Usuari();
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




}
