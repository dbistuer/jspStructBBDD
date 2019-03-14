package cat.almata.daw.llistatUsuaris;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

public class EditUserAction extends DBAction implements SessionAware{

	private static final long serialVersionUID = 1L;

	private Map<String, Object> session;
	private Usuari usuari = null;
	private Usuari usuariSessio = null;
	
	public String updateUser() {
		
		usuariSessio = (Usuari) session.get(Constants.sessioUsuari);
		if(usuariSessio!=null) {
			cargaDB();
			if(db.loginUsuari(usuariSessio.getLogin(),usuariSessio.getPass())) {
				if(usuari.getPass().equals(usuari.getPassword())) {
					if(!usuari.getLogin().isEmpty() && !usuari.getPass().isEmpty()) {
						db.updateUser(usuari, usuariSessio.getLogin());
						session.remove(Constants.sessioUsuari);
						session.put(Constants.sessioUsuari, usuari);
						return SUCCESS;
					}else {
						addActionError("editUser.notNull");
						return INPUT;
					}
				}else {
					addActionError("editUser.equalsPass");
					return INPUT;
				}
			}else {
				addActionError("editUser.errorUserNotExists");
				return "login";
			}
	
			
		}else {
			// Si no tenim dades d'usuari, fem un new i tornem al formulari
			usuari = new Usuari();
			return INPUT;
		}
	}

	public String editUser() {
		usuariSessio = (Usuari) session.get(Constants.sessioUsuari);
		if(usuariSessio!=null) {
			cargaDB();
			if(db.loginUsuari(usuariSessio.getLogin(),usuariSessio.getPass())) {
				usuari = new Usuari();
				usuari = db.getUsuari(usuariSessio.getLogin());
				return INPUT;
			}else {
				addActionError("editUser.errorUserNotExists");
				return "login";
			}
		}else {
			addActionError("no usuari en sessio");
			return "login";
		}
	}
	
	public void cargaDB() {
		if(db==null) {
			this.loadDB();
		}
	}

	public Usuari getUsuari() {
		return usuari;
	}

	public void setUsuari(Usuari usuari) {
		this.usuari = usuari;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=(SessionMap<String, Object>) session;
	}
}
