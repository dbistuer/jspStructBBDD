package cat.almata.daw.llistatUsuaris;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;
import java.util.Hashtable;
import java.util.List;


public class GestorBd {
	
	/*****************Atributs******************/
	
	private String hostname;
	private String database;
	private String port;
	private String userLogin;
	private String userPasswd;
	private Connection conn;
	
	/***************Constructors*****************/
	
	public GestorBd(){
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException ex) {
			System.out.println("Error: unable to load driver class!");
		}
	}
	
	public GestorBd(String hostname, String database, String port, String user, String passwd) {
		this(hostname,database,user,passwd);
		this.port=port;
	}

	public GestorBd(String hostname, String database, String user, String passwd) {
		this();
		this.hostname = hostname;
		this.database = database;
		this.userLogin = user;
		this.userPasswd = passwd;
		
	}

	/***********Metodes de negoci*************/
	
	public void crearProducte(Producte producte) {
		int retorn = 0;
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){

			String sql = "INSERT INTO "+database+".producte(idUsuari,nom,disponibilitat,descripcio,preu,iniciVenda) VALUES( ? , ? , ? , ? , ? , ? );";
			try(PreparedStatement insertedProduct = conn.prepareStatement(sql)){
				

				insertedProduct.setInt(1,producte.getIdUsuari());
				insertedProduct.setString(2,producte.getNom());
				insertedProduct.setInt(3,producte.getDisponibilitat());
				insertedProduct.setString(4,(producte.getDescripcio()));
				insertedProduct.setFloat(5,producte.getPreu());
				java.sql.Date dinici= new java.sql.Date(producte.getDataInici().getTime());
				insertedProduct.setDate(6,dinici);
				retorn = insertedProduct.executeUpdate();
				System.out.println(retorn+" fila\'es insertades");
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		
	}
	
	public Collection<Producte> obtenirProductes(){
		
		Collection<Producte> productes = new ArrayList<Producte>();
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){
			
			String sql = "SELECT * FROM "+database+".producte ORDER BY preu ASC;";
			try(PreparedStatement usersFound = conn.prepareStatement(sql)){
				
				
				try(ResultSet rs = usersFound.executeQuery()){
					
					while(rs.next()){
						Producte producte = new Producte(rs.getInt("id"),rs.getInt("idUsuari"),rs.getString("nom"),rs.getInt("disponibilitat"),rs.getString("descripcio"),rs.getFloat("preu"),rs.getDate("iniciVenda"));
						productes.add(producte);
					}
					
				}catch(SQLException rse){
					rse.printStackTrace();
				}
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		return productes;
		
	}

	public Producte obtenirProducte(int id){
		Producte producte=null;
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){
			
			String sql = "SELECT * FROM "+database+".producte WHERE id = ?;";
			try(PreparedStatement userFound = conn.prepareStatement(sql)){
				
				userFound.setInt(1, id);
				
				try(ResultSet rs = userFound.executeQuery()){
					
					while(rs.next()){
						producte = new Producte(rs.getInt("id"),rs.getInt("idUsuari"),rs.getString("nom"),rs.getInt("disponibilitat"),rs.getString("descripcio"),rs.getFloat("preu"),rs.getDate("iniciVenda"));
					}
					
				}catch(SQLException rse){
					rse.printStackTrace();
				}
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		return producte;
	}
	
	public Producte obtenirProducteCarro(int id){
		Producte producte=null;
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){
			
			String sql = "SELECT producte.id,producte.nom,producte.preu,COUNT(cantitat) AS cantitat,producte.descripcio,producte.idUsuari FROM "+database+".carrito, "+database+".producte WHERE carrito.idProducte=producte.id AND carrito.idUsuari = ? GROUP BY idProducte ORDER BY preu ASC;";
			try(PreparedStatement usersFound = conn.prepareStatement(sql)){
				
				usersFound.setInt(1, id);
				
				try(ResultSet rs = usersFound.executeQuery()){
					
					while(rs.next()){
						producte = new Producte(rs.getInt("id"),rs.getString("nom"),rs.getFloat("preu"),rs.getInt("cantitat"),rs.getString("descripcio"),rs.getInt("idUsuari")); 
					}
					
				}catch(SQLException rse){
					rse.printStackTrace();
				}
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		return producte;
	}
	
	public Collection<Producte> filtrarProductes(Date dataInici,Date dataFi){
		Collection<Producte> productes = new ArrayList<Producte>();
				
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){
			
			String sql = "SELECT * FROM "+database+".producte WHERE iniciVenda BETWEEN ? AND  ? ORDER BY preu ASC;";
			try(PreparedStatement usersFiltro = conn.prepareStatement(sql)){
				
				usersFiltro.setDate(1,dataInici);
				usersFiltro.setDate(2,dataFi);
				
				try(ResultSet rs = usersFiltro.executeQuery()){
					
					while(rs.next()){
						Producte producte = new Producte(rs.getInt("id"),rs.getInt("idUsuari"),rs.getString("nom"),rs.getInt("disponibilitat"),rs.getString("descripcio"),rs.getFloat("preu"),rs.getDate("iniciVenda"));
						productes.add(producte);
					}
					
				}catch(SQLException rse){
					rse.printStackTrace();
				}
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		return productes;
	}
	
	public Collection<Producte> filtrarProductes(int preuMinim,int preuMaxim){
		Collection<Producte> productes = new ArrayList<Producte>();
				
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){
			
			String sql = "SELECT * FROM "+database+".producte WHERE preu BETWEEN ? AND  ? ORDER BY preu ASC;";
			try(PreparedStatement usersFiltro = conn.prepareStatement(sql)){
				
				usersFiltro.setInt(1,preuMinim);
				usersFiltro.setInt(2,preuMaxim);
				
				try(ResultSet rs = usersFiltro.executeQuery()){
					
					while(rs.next()){
						Producte producte = new Producte(rs.getInt("id"),rs.getInt("idUsuari"),rs.getString("nom"),rs.getInt("disponibilitat"),rs.getString("descripcio"),rs.getFloat("preu"),rs.getDate("iniciVenda"));
						productes.add(producte);
					}
					
				}catch(SQLException rse){
					rse.printStackTrace();
				}
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		return productes;
	}
	
 	public int producteAlCarro(Producte producte,int id) {
		int retorn = 0;
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){

			String sql = "INSERT INTO "+database+".carrito(idUsuari,idProducte,cantitat)  VALUES(?,?,?) ";
			try(PreparedStatement compraProduct = conn.prepareStatement(sql)){
				

				compraProduct.setInt(1,id);
				compraProduct.setInt(2,producte.getId());
				compraProduct.setInt(3,1);
				
				retorn = compraProduct.executeUpdate();
			
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		return retorn;
	}
	
 	public Collection<Producte> obtenirProductesCarro(int id){
 		Collection<Producte> productes = new ArrayList<Producte>();
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){
			
			String sql = "SELECT producte.id,producte.nom,producte.preu,COUNT(cantitat) AS cantitat,producte.descripcio,producte.idUsuari FROM "+database+".carrito, "+database+".producte WHERE carrito.idProducte=producte.id AND carrito.idUsuari = ? GROUP BY idProducte ORDER BY preu ASC;";
			try(PreparedStatement usersFound = conn.prepareStatement(sql)){
				
				usersFound.setInt(1, id);
				
				try(ResultSet rs = usersFound.executeQuery()){
					
					while(rs.next()){
						Producte producte = new Producte(rs.getInt("id"),rs.getString("nom"),rs.getFloat("preu"),rs.getInt("cantitat"),rs.getString("descripcio"),rs.getInt("idUsuari"));
						productes.add(producte); 
					}
					
				}catch(SQLException rse){
					rse.printStackTrace();
				}
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		return productes;
 	}
 	
	/***/

 	
 	public void updProducte(Producte producte) {
		int retorn = 0;
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){

			String sql = "UPDATE "+database+".producte SET nom=?, disponibilitat=?, descripcio=?, preu=?, iniciVenda=? WHERE id=?;";
			try(PreparedStatement updProduct = conn.prepareStatement(sql)){
				

				updProduct.setString(1,producte.getNom());
				updProduct.setInt(2,producte.getDisponibilitat());
				updProduct.setString(3,producte.getDescripcio());
				updProduct.setFloat(4,producte.getPreu());
				java.sql.Date dinici= new java.sql.Date(producte.getDataInici().getTime());
				updProduct.setDate(5,dinici);
				updProduct.setInt(6,producte.getIdUsuari());
				updProduct.addBatch();
				
				updProduct.executeBatch();
			
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		
	}
 	

	
	public int compraProducte(Producte producte,int idU) {
		int retorn = 0;
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){
			
			String sqlT = "SET @trigger_on = ?;";
			try(PreparedStatement setTrigg = conn.prepareStatement(sqlT)){
				
				setTrigg.setInt(1,0);
				
				setTrigg.executeUpdate();
			
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}
			
			String sql = "DELETE FROM "+database+".carrito  WHERE idUsuari= ? AND idProducte = ?;";
			try(PreparedStatement compraProduct = conn.prepareStatement(sql)){
				
				compraProduct.setInt(1,idU);
				compraProduct.setInt(2,producte.getId());
				
				compraProduct.executeUpdate();
			
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		return retorn;
		
	}
	
	public int eliminaProducte(Producte producte,int idU) {
		int retorn = 0;
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){

			String sqlT = "SET @trigger_on = ?;";
			try(PreparedStatement setTrigg = conn.prepareStatement(sqlT)){
				
				setTrigg.setInt(1,1);
				
				setTrigg.executeUpdate();
			
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}
			
			String sql = "DELETE FROM "+database+".carrito  WHERE idUsuari= ? AND idProducte = ?;";
			try(PreparedStatement eliminarProduct = conn.prepareStatement(sql)){
				
				eliminarProduct.setInt(1,idU);
				eliminarProduct.setInt(2,producte.getId());

				retorn = eliminarProduct.executeUpdate();
			
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		return retorn;
		
	}
	
	public void SetTrigger(int var) {
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){

			String sql = "SET @trigger_on = ?;";
			try(PreparedStatement setTrigg = conn.prepareStatement(sql)){
				
				setTrigg.setInt(1,var);
				
				setTrigg.executeUpdate();
			
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
	}
	/**************************************************************************************************************/
	
	public Collection<Usuari> obtenirUsuaris(){
		
		
		ArrayList<Usuari> usuaris = new ArrayList<Usuari>();
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){
			
			String sql = "SELECT * FROM "+database+".Usuaris;";
			try(PreparedStatement usersFound = conn.prepareStatement(sql)){
				
				
				try(ResultSet rs = usersFound.executeQuery()){
					
					while(rs.next()){
						Usuari usuari = new Usuari(rs.getString("login"),rs.getString("nom"));
						usuaris.add(usuari);
					}
					
				}catch(SQLException rse){
					rse.printStackTrace();
				}
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		return usuaris;
		
	}
	
	public boolean loginUsuari(String login, String pass) {
		boolean retorn = false;
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){

			String sql = "SELECT COUNT(*) AS NUMERO FROM "+database+".usuari WHERE login LIKE ? AND pass LIKE ?;";
			try(PreparedStatement loginUser = conn.prepareStatement(sql)){
				
				loginUser.setString(1,login);
				loginUser.setString(2,pass);
				
				try(ResultSet rs = loginUser.executeQuery()){
						if(rs.next()) {
							if(rs.getInt(1) > 0) retorn = true;
							else retorn = false;
						}
					
				}catch(SQLException rse){
					rse.printStackTrace();
				}
			
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		return retorn;
		
	}
	
	public Usuari getUsuari(String login) {
		Usuari usuari = null;
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){

			String sql = "SELECT * FROM "+database+".usuari WHERE login LIKE ?;";
			try(PreparedStatement loginUser = conn.prepareStatement(sql)){
				
				loginUser.setString(1,login);
				
				try(ResultSet rs = loginUser.executeQuery()){
						if(rs.next()) {
							usuari = new Usuari(rs.getInt("id"),rs.getString("nom"),rs.getString("cognom"),rs.getString("login"),rs.getString("pass"),rs.getString("mail"));
						}
					
				}catch(SQLException rse){
					rse.printStackTrace();
				}
			
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		return usuari;
	}
	
	public void crearUsuaris() {
		int retorn = 0;
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+this.hostname+"/"+this.database,this.userLogin,this.userPasswd)){

			String sql = "INSERT INTO "+database+".Usuaris VALUES(?, ?);";
			try(PreparedStatement insertedUser = conn.prepareStatement(sql)){
				
				/*insertedUser.setString(2,producte.getNom());
				insertedUser.setInt(3,producte.getDisponibilitat());
				insertedUser.setString(4,producte.getDescripcio());
				insertedUser.setFloat(5,producte.getPreu());
				insertedUser.setDate(6,(java.sql.Date) producte.getDataInici());
				insertedUser.executeQuery();*/
			
			}catch(SQLException stmte){
				stmte.printStackTrace();
			}

		} catch (SQLException conne) {
			conne.printStackTrace();
		}
		
	}

	/****************Setters && Getters****************/
	
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserPasswd() {
		return userPasswd;
	}

	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}

}
