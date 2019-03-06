package cat.almata.daw.llistatUsuaris;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Producte {
	
	private int id;
	private String nom;
	private int disponibilitat;
	private String descripcio;
	private float preu;
	private int idUsuari;
	private Date dataInici;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	
	
	public Producte(){
		super();
	}
	
	public Producte(String nom, int disponibilitat, int idUsuari) {
		this();
		this.nom = nom;
		this.disponibilitat = disponibilitat;
		this.idUsuari = idUsuari;
	}

	public Producte(String nom, int disponibilitat, int idUsuari, String descripcio,
			float preu, String fechaInici) throws ParseException {
		this(nom,disponibilitat, idUsuari);
		this.descripcio = descripcio;
		this.preu = preu;
		setFechaInici(fechaInici);
		this.dataInici=sdf.parse(fechaInici);
	}
	
	public Producte(int id,int idUsuari,String nom,int disponibilitat,String descripcio,float preu,Date iniciVenda){
		this(nom,disponibilitat, idUsuari);
		this.descripcio = descripcio;
		this.preu = preu;
		this.dataInici=iniciVenda;
		this.id=id;
	}

	public Date getDataInici() {
		return dataInici;
	}

	public void setDataInici(Date dataInici) {
		this.dataInici = dataInici;
	}

	public String getFechaInici() {
		return sdf.format(dataInici);
	}

	public void setFechaInici(String fechaInici) {
		try {
			this.dataInici = sdf.parse(fechaInici);
		}catch(Exception e) {
			this.dataInici = new Date();
		}
	}

	public int getIdUsuari() {
		return idUsuari;
	}

	public void setIdUsuari(int idUsuari) {
		this.idUsuari = idUsuari;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getDisponibilitat() {
		return disponibilitat;
	}

	public void setDisponibilitat(int disponibilitat) {
		this.disponibilitat = disponibilitat;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public float getPreu() {
		return preu;
	}

	public void setPreu(float preu) {
		this.preu = preu;
	}

	public int getId() {
		return id;
	}
	
}
