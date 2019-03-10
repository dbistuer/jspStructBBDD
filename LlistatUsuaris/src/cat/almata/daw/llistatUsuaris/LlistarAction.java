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

public class LlistarAction extends DBAction implements ApplicationAware,SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Producte> productes;
	private Map<Integer, Boolean> checkboxes;
	private Producte producte;
	private Usuari usuari = null;
	private String actiu = "Actiu";
	private Date dataInici = null;
	private Date dataFi = null;
	private int preuMinim;
	private int preuMaxim;

	
	private Map<String, Object> application;
	private Map<String, Object> session;

	
	public String llistarProductes() {
		// Aquí hem d'agafar de session la llista d'usuaris
		// En cas que no existeixi el crearem i el posarem 
		// en l'àmbit session.
		// si existeix ho passarem a una Collection
		setCheckboxes(checkboxes);
		cargaDB();
		
		usuari = (Usuari)session.get(Constants.sessioUsuari);
		if(usuari != null) {
			ArrayList<Producte> llistatProductes = (ArrayList<Producte>) db.obtenirProductes();
		if(llistatProductes == null) {
			llistatProductes = new ArrayList<Producte>();
		}
		//application.put(Constants.llistatProductes, llistatProductes);
		productes = llistatProductes;
		return SUCCESS;
		}else {
			return "no user";
		}
		
	}
	
	public String filtrarProductes() throws ParseException {
		
		cargaDB();
		
		usuari = (Usuari)session.get(Constants.sessioUsuari);
		if(usuari != null) {
			if(dataInici != null && dataFi != null) {
				java.sql.Date sqlDI = new java.sql.Date(dataInici.getTime());
				java.sql.Date sqlDF = new java.sql.Date(dataFi.getTime());
				ArrayList<Producte> llistatProductes = (ArrayList<Producte>) db.filtrarProductes(sqlDI,sqlDF);
				if(llistatProductes == null)
					llistatProductes = new ArrayList<Producte>();
				productes = llistatProductes;
				return SUCCESS;
			}else {
				return "feches = null";
			}
		}else {
			return "no user";
		}
	}

	public String filtrarPreu() {
		cargaDB(); 
		
		usuari = (Usuari)session.get(Constants.sessioUsuari);
		if(usuari != null) {
			ArrayList<Producte> llistatProductes = (ArrayList<Producte>) db.filtrarProductes(preuMinim,preuMaxim);
			if(llistatProductes == null)
				llistatProductes = new ArrayList<Producte>();
			productes = llistatProductes;
			return SUCCESS;
		}else {
			return "no user";
		}
	}
 	
	public String afegirCarro() {
		cargaDB();
		setCheckboxes(checkboxes);
		if(checkboxes!=null){
			Iterator<Map.Entry<Integer, Boolean>> entries = checkboxes.entrySet().iterator();
			while (entries.hasNext()) {
			    Map.Entry<Integer, Boolean> entry = entries.next();
			    if(entry.getValue()) {
				    producte = db.obtenirProducte(entry.getKey().intValue());
				    if(producte.getDisponibilitat()<1) {
				    	return "NoEnQueda";
				    }else {
				    	Usuari u = (Usuari) session.get(Constants.sessioUsuari);
				    	u = db.getUsuari(u.getLogin());
				    	int e=db.producteAlCarro(producte,u.getId());
				    }
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
	
	public int getPreuMinim() {
		return preuMinim;
	}

	public void setPreuMinim(int preuMinim) {
		this.preuMinim = preuMinim;
	}

	public int getPreuMaxim() {
		return preuMaxim;
	}

	public void setPreuMaxim(int preuMaxim) {
		this.preuMaxim = preuMaxim;
	}

	public Date getDataInici() {
		return dataInici;
	}

	public void setDataInici(Date dataInici) {
		this.dataInici = dataInici;
	}

	public Date getDataFi() {
		return dataFi;
	}

	public void setDataFi(Date dataFi) {
		this.dataFi = dataFi;
	}
	
	public Map<String, Object> getApplication() {
		return application;
	}
	
	public Producte getProducte() {
		return producte;
	}

	public void setProducte(Producte producte) {
		this.producte = producte;
	}
	
	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
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
