package cat.almata.daw.llistatUsuaris;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends DBAction implements SessionAware{

	private SessionMap<String, Object> session;
	private Usuari usuari = null;
	private Date actual = null;
	
	
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
	
	public String login(){
		cargaDB();
		if(db.loginUsuari(usuari.getLogin(), usuari.getPass())){
			usuari = db.getUsuari(usuari.getLogin());
			session.put(Constants.sessioUsuari, usuari);
			return SUCCESS;
		}else {
			addActionError(getText("login.error"));
			return "login";
		}	
	}
	
	public String logout(){
		usuari = (Usuari)session.get(Constants.sessioUsuari);
		session.invalidate();
		return SUCCESS;
	}
	
	public void cargaDB() {
		if(db==null) {
			this.loadDB();
		}
	}

}
